package com.example.paymentservice.adapter.out.web.toss.executor

import com.example.paymentservice.application.port.`in`.PaymentConfirmCommand
import com.example.paymentservice.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

interface PaymentExecutor {

  fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult>
}