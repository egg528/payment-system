package com.example.paymentservice.adapter.out.persistent.exception

import com.example.paymentservice.domain.PaymentStatus


class PaymentAlreadyProcessedException(
  val status: PaymentStatus,
  message: String
) : RuntimeException(message) {
}