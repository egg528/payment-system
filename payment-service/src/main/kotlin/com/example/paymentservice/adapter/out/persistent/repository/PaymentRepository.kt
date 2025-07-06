package com.example.paymentservice.adapter.out.persistent.repository

import com.example.paymentservice.domain.PaymentEvent
import com.example.paymentservice.domain.PendingPaymentEvent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentRepository {

    fun save(paymentEvent: PaymentEvent): Mono<Void>

    fun getPendingPayments(): Flux<PendingPaymentEvent>
}