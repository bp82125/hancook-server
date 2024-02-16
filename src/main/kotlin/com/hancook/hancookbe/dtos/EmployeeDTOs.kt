package com.hancook.hancookbe.dtos

import com.hancook.hancookbe.enums.Gender
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class RequestEmployeeDto(
    @field:NotEmpty(message = "Name is required")
    val name: String,

    @field:NotNull(message = "Gender is required")
    val gender: Gender,

    @field:NotEmpty(message = "Address is required")
    val address: String,

    @field:NotEmpty(message = "Address is required")
    val phoneNumber: String,

    val positionId: UUID,
    val accountId: UUID?
)

data class ResponseEmployeeDto(
    val id: UUID?,
    val name: String,
    val gender: Gender,
    val address: String,
    val phoneNumber: String,
    val position: ResponsePositionDto,
    val account: ResponseAccountDto?
)