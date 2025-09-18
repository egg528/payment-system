package com.example.walletservice.adapter.out.persistence.repository

import com.example.walletservice.domain.PaymentOrder

interface PaymentOrderRepository {

  fun getPaymentOrders(orderId: String): List<PaymentOrder>
}