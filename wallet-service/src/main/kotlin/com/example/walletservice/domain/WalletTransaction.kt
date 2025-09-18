package com.example.walletservice.domain

data class WalletTransaction (
  val walletId: Long,
  val amount: Long,
  val type: TransactionType,
  val referenceId: Long,
  val referenceType: ReferenceType,
  val orderId: String
)
