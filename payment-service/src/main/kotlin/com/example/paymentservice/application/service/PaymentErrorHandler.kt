package com.example.paymentservice.application.service

import com.example.paymentservice.adapter.out.persistent.exception.PaymentAlreadyProcessedException
import com.example.paymentservice.adapter.out.persistent.exception.PaymentValidationException
import com.example.paymentservice.adapter.out.web.toss.exception.PSPConfirmationException
import com.example.paymentservice.application.port.`in`.PaymentConfirmCommand
import com.example.paymentservice.application.port.out.LoadPendingPaymentPort
import com.example.paymentservice.application.port.out.PaymentStatusUpdateCommand
import com.example.paymentservice.application.port.out.PaymentStatusUpdatePort
import com.example.paymentservice.domain.PaymentConfirmationResult
import com.example.paymentservice.domain.PaymentFailure
import com.example.paymentservice.domain.PaymentStatus
import io.netty.handler.timeout.TimeoutException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PaymentErrorHandler (
    private val paymentStatusUpdatePort: PaymentStatusUpdatePort
) {
    fun handlePaymentConfirmationError (error: Throwable, command: PaymentConfirmCommand): Mono<PaymentConfirmationResult> {
        val (status, failure) = when (error) {
            is PSPConfirmationException -> Pair(error.paymentStatus(), PaymentFailure(error.errorCode, error.errorMessage))
            is PaymentValidationException -> Pair(PaymentStatus.FAILURE, PaymentFailure(error::class.simpleName ?: "", error.message ?: ""))
            is PaymentAlreadyProcessedException -> return Mono.just(PaymentConfirmationResult(PaymentStatus.SUCCESS))
            is TimeoutException -> Pair(PaymentStatus.UNKNOWN, PaymentFailure(error::class.simpleName ?: "", error.message ?: ""))
            else -> Pair(PaymentStatus.UNKNOWN, PaymentFailure(error::class.simpleName ?: "", error.message ?: ""))
        }

        val paymentStatusUpdateCommand = PaymentStatusUpdateCommand(
            paymentKey = command.paymentKey,
            orderId = command.orderId,
            status = status,
            failure = failure
        )

        return paymentStatusUpdatePort.updatePaymentStatus(paymentStatusUpdateCommand)
            .map { PaymentConfirmationResult(status, failure) }
    }
}