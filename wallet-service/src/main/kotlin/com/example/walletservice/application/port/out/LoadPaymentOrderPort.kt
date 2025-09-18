package com.example.walletservice.wallet.application.port.out

import com.example.walletservice.domain.PaymentOrder

interface LoadPaymentOrderPort {

  fun getPaymentOrders(orderId: String): List<PaymentOrder>
}