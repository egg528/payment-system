package com.example.paymentservice.adapter.`in`.web.api

import com.example.paymentservice.adapter.`in`.web.request.TossPaymentRequest
import com.example.paymentservice.adapter.`in`.web.response.ApiResponse
import com.example.paymentservice.adapter.out.web.toss.executor.TossPaymentExecutor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/toss")
class TossPaymentController (
    private val tossPaymentExecutor: TossPaymentExecutor
) {

    @PostMapping("/confirm")
    fun confirm(@RequestBody request: TossPaymentRequest): Mono<ResponseEntity<ApiResponse<String>>> {
        return tossPaymentExecutor.execute(
            paymentKey = request.paymentKey,
            orderId = request.orderId,
            amount = request.amount.toString()
        ).map {
            ResponseEntity.ok().body(
                ApiResponse.with("ok", it)
            )
        }
    }
}