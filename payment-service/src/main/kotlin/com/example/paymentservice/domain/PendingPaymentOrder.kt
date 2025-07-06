package com.example.paymentservice.domain

data class PendingPaymentOrder(
    val pendingOrderId: Long,
    val status: PaymentStatus,
    val amount: Long,
    val failedCount: Byte,
    val threshold: Byte
)
