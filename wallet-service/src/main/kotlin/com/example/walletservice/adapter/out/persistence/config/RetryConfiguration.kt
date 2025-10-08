package com.example.walletservice.adapter.out.persistence.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.retry.support.RetryTemplate

@Configuration
class RetryConfiguration {

    @Bean
    fun retryTemplate(): RetryTemplate {
        return RetryTemplate.builder()
            .maxAttempts(4) // 초기 시도 1회 + 재시도 3회
            .retryOn(OptimisticLockingFailureException::class.java)
            .uniformRandomBackoff(50, 150) // 50ms ~ 150ms 랜덤 백오프 (jitter 자동 적용)
            .build()
    }
}
