package com.example.paymentservice.adapter.out.web.toss

import com.example.paymentservice.adapter.out.web.toss.executor.PaymentExecutor
import com.example.paymentservice.application.port.`in`.PaymentConfirmCommand
import com.example.paymentservice.application.port.out.PaymentExecutorPort
import com.example.paymentservice.domain.PaymentExecutionResult
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PaymentExecutorWebAdapter (
  private val paymentExecutor: PaymentExecutor
) : PaymentExecutorPort {

  override fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult> {
    return paymentExecutor.execute(command)
  }
}