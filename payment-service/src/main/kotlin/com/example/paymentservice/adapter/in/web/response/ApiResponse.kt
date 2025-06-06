package com.example.paymentservice.adapter.`in`.web.response

import org.springframework.http.HttpStatus

data class ApiResponse<T> (
    val status: String = "success",
    val message: String = "",
    val data: T? = null
) {
    companion object {
        fun <T> with(message: String, data: T?) =
            ApiResponse(message = message, data = data)
    }
}
