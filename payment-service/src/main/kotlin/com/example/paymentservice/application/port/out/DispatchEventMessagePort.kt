package com.example.paymentservice.application.port.out

import com.example.paymentservice.domain.PaymentEventMessage

interface DispatchEventMessagePort {

    fun dispatch(paymentEventMessage: PaymentEventMessage)
}