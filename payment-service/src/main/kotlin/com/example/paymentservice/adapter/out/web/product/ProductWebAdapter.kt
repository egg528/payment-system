package com.example.paymentservice.adapter.out.web.product


import com.example.paymentservice.adapter.out.web.product.client.ProductClient
import com.example.paymentservice.application.port.out.LoadProductPort
import com.example.paymentservice.domain.Product
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class ProductWebAdapter (
  private val productClient: ProductClient
) : LoadProductPort {

  override fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product> {
    return productClient.getProducts(cartId, productIds)
  }
}