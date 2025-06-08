package com.example.paymentservice.adapter.out.persistent.repository

import com.example.paymentservice.domain.PaymentEvent
import reactor.core.publisher.Mono

interface PaymentRepository {

    fun save(paymentEvent: PaymentEvent): Mono<Void>
}