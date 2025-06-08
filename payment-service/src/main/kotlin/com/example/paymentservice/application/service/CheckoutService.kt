package com.example.paymentservice.application.service

import com.example.paymentservice.application.port.`in`.CheckoutCommand
import com.example.paymentservice.application.port.`in`.CheckoutUseCase
import com.example.paymentservice.application.port.out.LoadProductPort
import com.example.paymentservice.application.port.out.SavePaymentPort
import com.example.paymentservice.domain.CheckoutResult
import com.example.paymentservice.domain.PaymentEvent
import com.example.paymentservice.domain.Product
import com.example.paymentservice.domain.PaymentOrder
import com.example.paymentservice.domain.PaymentStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CheckoutService (
  private val loadProductPort: LoadProductPort,
  private val savePaymentPort: SavePaymentPort
) : CheckoutUseCase {

  override fun checkout(command: CheckoutCommand): Mono<CheckoutResult> {
    return loadProductPort.getProducts(command.cartId, command.productIds)
      .collectList()
      .map { createPaymentEvent(command, it) }
      .flatMap { savePaymentPort.save(it).thenReturn(it)  }
      .map { CheckoutResult(amount = it.totalAmount(), orderId = it.orderId, orderName = it.orderName) }
  }

  private fun createPaymentEvent(command: CheckoutCommand, products: List<Product>): PaymentEvent {
    return PaymentEvent(
      buyerId = command.buyerId,
      orderId = command.idempotencyKey,
      orderName = products.joinToString { it.name },
      paymentOrders = products.map {
        PaymentOrder(
          sellerId = it.sellerId,
          orderId = command.idempotencyKey,
          productId = it.id,
          amount = it.amount,
          paymentStatus = PaymentStatus.NOT_STARTED,
        )
      }
    )
  }
}