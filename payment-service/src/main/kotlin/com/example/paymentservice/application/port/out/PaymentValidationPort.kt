package com.example.paymentservice.application.port.out

import reactor.core.publisher.Mono

interface PaymentValidationPort {

  fun isValid(orderId: String, amount: Long): Mono<Boolean>
}