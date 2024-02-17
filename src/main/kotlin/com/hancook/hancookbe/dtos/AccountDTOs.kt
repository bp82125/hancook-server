package com.hancook.hancookbe.dtos

import com.hancook.hancookbe.enums.Role
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class RequestAccountDto(
    @field:NotEmpty(message = "Username is required")
    val username: String,

    @field:NotEmpty(message = "Password is required")
    val password: String,

    @field:NotNull(message = "Role is required")
    val role: Role, // Add role field

    val enabled: Boolean,

    val employeeId: UUID? = null
)

data class ResponseAccountDto(
    val id: UUID?,
    val username: String,
    val role: Role,
    val enabled: Boolean
)