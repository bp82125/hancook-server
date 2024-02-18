package com.hancook.hancookbe.system

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

data class ApiResponse<T>(
    val success: Boolean,
    val statusCode: Int,
    val data: T? = null,
    val message: String? = null
)