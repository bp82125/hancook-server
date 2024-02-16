package com.hancook.hancookbe.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class RequestAccountDto(
    @field:NotEmpty(message = "Username is required")
    val username: String,

    @field:NotEmpty(message = "Password is required")
    val password: String,

    @field:NotNull(message = "Each account must assigned to an employee.")
    val employeeId: UUID
)

data class ResponseAccountDto(
    val id: UUID?,
    val username: String,
)