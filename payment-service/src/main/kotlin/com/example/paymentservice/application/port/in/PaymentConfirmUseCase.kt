package com.example.paymentservice.application.port.`in`

import com.example.paymentservice.domain.PaymentConfirmationResult
import reactor.core.publisher.Mono

interface PaymentConfirmUseCase {

  fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmationResult>
}