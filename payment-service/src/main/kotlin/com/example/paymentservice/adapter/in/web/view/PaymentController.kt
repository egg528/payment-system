package com.example.paymentservice.adapter.`in`.web.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
class PaymentController {
    @GetMapping("/success")
    fun success(): Mono<String> = Mono.just("success")

    @GetMapping("/fail")
    fun fail(): Mono<String> = Mono.just("fail")
}