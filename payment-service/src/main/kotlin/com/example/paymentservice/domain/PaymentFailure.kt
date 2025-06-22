package com.example.paymentservice.domain

data class PaymentFailure (
  val errorCode: String,
  val message: String
)