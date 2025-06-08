package com.example.paymentservice.domain

import java.math.BigDecimal

data class CheckoutResult (
  val amount: BigDecimal,
  val orderId: String,
  val orderName: String
)