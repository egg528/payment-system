package com.example.paymentservice.adapter.out.persistent

import com.example.paymentservice.adapter.out.persistent.repository.PaymentRepository
import com.example.paymentservice.adapter.out.persistent.repository.PaymentStatusUpdateRepository
import com.example.paymentservice.adapter.out.persistent.repository.PaymentValidationRepository
import com.example.paymentservice.application.port.out.LoadPendingPaymentPort
import com.example.paymentservice.application.port.out.PaymentStatusUpdateCommand
import com.example.paymentservice.application.port.out.PaymentStatusUpdatePort
import com.example.paymentservice.application.port.out.PaymentValidationPort
import com.example.paymentservice.application.port.out.SavePaymentPort
import com.example.paymentservice.domain.PaymentEvent
import com.example.paymentservice.domain.PendingPaymentEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PaymentPersistentAdapter (
    private val paymentRepository: PaymentRepository,
    private val paymentStatusUpdateRepository: PaymentStatusUpdateRepository,
    private val paymentValidationRepository: PaymentValidationRepository,
) : SavePaymentPort, PaymentStatusUpdatePort, PaymentValidationPort, LoadPendingPaymentPort {
    override fun save(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.save(paymentEvent)
    }

    override fun updatePaymentStatusToExecuting(orderId: String, paymentKey: String): Mono<Boolean> {
        return paymentStatusUpdateRepository.updatePaymentStatusToExecuting(orderId, paymentKey)
    }

    override fun isValid(orderId: String, amount: Long): Mono<Boolean> {
        return paymentValidationRepository.isValid(orderId, amount)
    }

    override fun updatePaymentStatus(command: PaymentStatusUpdateCommand): Mono<Boolean> {
        return paymentStatusUpdateRepository.updatePaymentStatus(command)
    }

    override fun getPendingPayments(): Flux<PendingPaymentEvent> {
        TODO("Not yet implemented")
    }
}