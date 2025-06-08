package com.example.paymentservice.test

import com.example.paymentservice.domain.PaymentEvent
import reactor.core.publisher.Mono

interface PaymentDatabaseHelper {

  fun getPayments(orderId: String): PaymentEvent?

  fun clean(): Mono<Void>
}