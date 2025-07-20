package com.example.paymentservice.application.port.out

import com.example.paymentservice.domain.PaymentEventMessage
import reactor.core.publisher.Flux

interface LoadPendingPaymentEventMessagePort {

    fun LoadPendingPaymentEventMessage(): Flux<PaymentEventMessage>
}