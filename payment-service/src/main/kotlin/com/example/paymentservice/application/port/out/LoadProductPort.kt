package com.example.paymentservice.application.port.out

import com.example.paymentservice.domain.Product
import reactor.core.publisher.Flux

interface LoadProductPort {

  fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product>
}