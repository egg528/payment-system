package com.example.walletservice.adapter.`in`.stream


import com.example.walletservice.domain.PaymentEventMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Consumer

@Configuration
class PaymentEventMessageHandler {

    @Bean
    fun consume(): Consumer<Message<PaymentEventMessage>> {
        return Consumer { message ->
            println(message.payload)
        }
    }
}