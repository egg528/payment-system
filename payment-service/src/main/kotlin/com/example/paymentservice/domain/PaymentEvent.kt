package com.example.paymentservice.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentEvent(
  val id: Long? = null,
  val buyerId: Long,
  val orderName: String,
  val orderId: String,
  val paymentKey: String? = null,
  val paymentType: PaymentType? = null,
  val paymentMethod: PaymentMethod? = null,
  val approvedAt: LocalDateTime? = null,
  val paymentOrders: List<PaymentOrder> = emptyList(),
  private var isPaymentDone: Boolean = false
) {

  fun totalAmount(): BigDecimal {
    return paymentOrders.sumOf { it.amount }
  }

  fun isPaymentDone(): Boolean {
    return isPaymentDone
  }
}