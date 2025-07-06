package com.example.paymentservice.application.service

import com.example.paymentservice.application.port.`in`.PaymentConfirmCommand
import com.example.paymentservice.application.port.`in`.PaymentRecoveryUseCase
import com.example.paymentservice.application.port.out.LoadPendingPaymentPort
import com.example.paymentservice.application.port.out.PaymentExecutorPort
import com.example.paymentservice.application.port.out.PaymentStatusUpdateCommand
import com.example.paymentservice.application.port.out.PaymentStatusUpdatePort
import com.example.paymentservice.application.port.out.PaymentValidationPort
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers
import java.util.concurrent.TimeUnit

@Component
@Profile("dev")
class PaymentRecoveryService (
    private val loadPendingPaymentPort: LoadPendingPaymentPort,
    private val paymentValidationPort: PaymentValidationPort,
    private val paymentExecutorPort: PaymentExecutorPort,
    private val paymentStatusUpdatePort: PaymentStatusUpdatePort,
    private val paymentErrorHandler: PaymentErrorHandler
) : PaymentRecoveryUseCase {

    private val recoveryScheduler = Schedulers.newSingle("recovery-subscriber")
    private val recoveryWorkerScheduler = Schedulers.newParallel("recovery-worker", 2)


    @Scheduled(fixedDelay = 180, initialDelay = 180, timeUnit = TimeUnit.SECONDS)
    override fun recovery() {
        loadPendingPaymentPort.getPendingPayments()
            .map {
                PaymentConfirmCommand(
                    paymentKey = it.paymentKey,
                    orderId = it.orderId,
                    amount = it.totalAmount()
                )
            }
            .parallel(2)
            .runOn(recoveryWorkerScheduler)
            .flatMap { command ->
                paymentValidationPort.isValid(command.orderId, command.amount).thenReturn(command)
                    .flatMap { paymentExecutorPort.execute(it) }
                    .flatMap { paymentStatusUpdatePort.updatePaymentStatus(PaymentStatusUpdateCommand(it)) }
                    .onErrorResume { paymentErrorHandler.handlePaymentConfirmationError(it, command).thenReturn(true) }
            }
            .sequential()
            .subscribeOn(recoveryScheduler)
            .subscribe()

    }
}