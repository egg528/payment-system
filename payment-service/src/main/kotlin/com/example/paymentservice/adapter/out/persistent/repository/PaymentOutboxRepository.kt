package com.example.paymentservice.adapter.out.persistent.repository

import com.example.paymentservice.application.port.out.PaymentStatusUpdateCommand
import com.example.paymentservice.domain.PaymentEventMessage
import com.example.paymentservice.domain.PaymentEventMessageType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentOutboxRepository {

    fun insertOutbox(command: PaymentStatusUpdateCommand): Mono<PaymentEventMessage>

    fun markMessageAsSent(idempotencyKey: String, type: PaymentEventMessageType): Mono<Boolean>

    fun makrMessageAsFailure(idempotencyKey: String, type: PaymentEventMessageType): Mono<Boolean>

    fun getPendingPaymentOutboxes(): Flux<PaymentEventMessage>
}