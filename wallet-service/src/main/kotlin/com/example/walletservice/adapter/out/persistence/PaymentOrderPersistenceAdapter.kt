package com.example.walletservice.adapter.out.persistence

import com.example.walletservice.common.PersistenceAdapter
import com.example.walletservice.domain.PaymentOrder
import com.example.walletservice.adapter.out.persistence.repository.PaymentOrderRepository
import com.example.walletservice.wallet.application.port.out.LoadPaymentOrderPort

@PersistenceAdapter
class PaymentOrderPersistenceAdapter (
  private val paymentOrderRepository: PaymentOrderRepository
) : LoadPaymentOrderPort {

  override fun getPaymentOrders(orderId: String): List<PaymentOrder> {
    return paymentOrderRepository.getPaymentOrders(orderId)
  }
}