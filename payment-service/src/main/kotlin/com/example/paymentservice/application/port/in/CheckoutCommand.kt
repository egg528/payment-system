package com.example.paymentservice.application.port.`in`

data class CheckoutCommand (
    val cartId: Long,
    val buyerId: Long,
    val productIds: List<Long>,
    val idempotencyKey: String
)
