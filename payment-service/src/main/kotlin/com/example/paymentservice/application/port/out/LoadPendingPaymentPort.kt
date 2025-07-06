package com.example.paymentservice.application.port.out

import com.example.paymentservice.domain.PendingPaymentEvent
import reactor.core.publisher.Flux

interface LoadPendingPaymentPort {

    fun getPendingPayments(): Flux<PendingPaymentEvent>
}