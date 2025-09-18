package com.example.walletservice.adapter.out.persistence.repository

import com.example.walletservice.domain.PaymentEventMessage
import com.example.walletservice.domain.WalletTransaction


interface WalletTransactionRepository {

  fun isExist(paymentEventMessage: PaymentEventMessage): Boolean

  fun save(walletTransactions: List<WalletTransaction>)
}