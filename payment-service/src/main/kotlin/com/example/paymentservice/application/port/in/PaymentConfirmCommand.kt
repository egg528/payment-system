package com.example.paymentservice.application.port.`in`

data class PaymentConfirmCommand (
  val paymentKey: String,
  val orderId: String,
  val amount: Long
)