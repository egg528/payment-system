package com.example.paymentservice.adapter.`in`.web.request

import java.time.LocalDateTime

data class CheckoutRequest (
  val cartId: Long = 1,
  val productIds: List<Long> = listOf(1, 2, 3),
  val buyerId: Long = 1,
  // 해당 주문을 식별할 수 있는 무언가가 필요함
  // 단순히 LocalDateTime.now().toString()로는 안 됨
  // 정책 결정이 필요해 보인다.
  val seed: String = LocalDateTime.now().toString()
)