package com.example.paymentservice.adapter.`in`.web.request

data class TossPaymentRequest(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
)
