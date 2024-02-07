package com.hancook.hancookbe.system

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)