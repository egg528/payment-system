package com.example.paymentservice.adapter.out.persistent.repository

import reactor.core.publisher.Mono

interface PaymentValidationRepository {

  fun isValid(orderId: String, amount: Long): Mono<Boolean>
}