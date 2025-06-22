package com.example.paymentservice.application.port.out

import com.example.paymentservice.application.port.`in`.PaymentConfirmCommand
import com.example.paymentservice.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

interface PaymentExecutorPort {

  fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult>
}