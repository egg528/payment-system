package com.example.paymentservice.application.port.out

import com.example.paymentservice.domain.PaymentEvent
import reactor.core.publisher.Mono

interface SavePaymentPort {

  fun save(paymentEvent: PaymentEvent): Mono<Void>
}