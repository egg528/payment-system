package com.example.paymentservice.adapter.`in`.web.view

import com.example.paymentservice.adapter.`in`.web.request.CheckoutRequest
import com.example.paymentservice.application.port.`in`.CheckoutCommand
import com.example.paymentservice.application.port.`in`.CheckoutUseCase
import com.example.paymentservice.common.IdempotencyCreator
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
class CheckoutController (
    private val checkoutUseCase: CheckoutUseCase
) {

    @GetMapping("/")
    fun checkoutPage(request: CheckoutRequest, model: Model): Mono<String> {
        val checkoutCommand = CheckoutCommand(
            cartId = request.cartId,
            buyerId = request.buyerId,
            productIds = request.productIds,
            idempotencyKey = IdempotencyCreator.create(request)
        )

        return checkoutUseCase.checkout(checkoutCommand)
            .map {
                model.addAttribute("orderId", it.orderId)
                model.addAttribute("orderName", it.orderName)
                model.addAttribute("amount", it.amount)
                "checkout"
            }
    }
}