package com.example.paymentservice.adapter.out.web.product.client

import com.example.paymentservice.domain.Product
import reactor.core.publisher.Flux

interface ProductClient {

  fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product>
}