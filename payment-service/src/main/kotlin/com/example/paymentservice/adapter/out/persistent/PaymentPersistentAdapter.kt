package com.example.paymentservice.adapter.out.persistent

import com.example.paymentservice.adapter.out.persistent.repository.PaymentRepository
import com.example.paymentservice.application.port.out.SavePaymentPort
import com.example.paymentservice.domain.PaymentEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PaymentPersistentAdapter (
    private val paymentRepository: PaymentRepository
) : SavePaymentPort {
    override fun save(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.save(paymentEvent)
    }
}