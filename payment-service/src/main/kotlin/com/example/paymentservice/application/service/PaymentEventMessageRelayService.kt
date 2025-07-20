package com.example.paymentservice.application.service

import com.example.paymentservice.application.port.`in`.PaymentEventMessageRelayUseCase
import com.example.paymentservice.application.port.out.DispatchEventMessagePort
import com.example.paymentservice.application.port.out.LoadPendingPaymentEventMessagePort
import com.example.paymentservice.common.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers
import java.util.concurrent.TimeUnit

@Component
class PaymentEventMessageRelayService (
    private val loadPendingPaymentEventMessagePort: LoadPendingPaymentEventMessagePort,
    private val dispatchEventMessagePort: DispatchEventMessagePort
): PaymentEventMessageRelayUseCase {

    private val scheduler = Schedulers.newSingle("message-relay")

    @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
    override fun relay() {
        loadPendingPaymentEventMessagePort.LoadPendingPaymentEventMessage()
            .map { dispatchEventMessagePort.dispatch(it) }
            .onErrorContinue { err, _ -> Logger.error("messageRelay", err.message ?: "failed to relay message", err) }
            .subscribeOn(scheduler)
            .subscribe()
    }
}