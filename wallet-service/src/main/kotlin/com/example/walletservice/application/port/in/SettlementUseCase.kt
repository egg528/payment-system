package com.example.walletservice.application.port.`in`

import com.example.walletservice.domain.PaymentEventMessage
import com.example.walletservice.domain.WalletEventMessage

interface SettlementUseCase {
    fun processSettlement(paymentEventMessage: PaymentEventMessage): WalletEventMessage
}