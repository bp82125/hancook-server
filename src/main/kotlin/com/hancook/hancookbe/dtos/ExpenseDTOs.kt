package com.hancook.hancookbe.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.time.LocalDateTime
import java.util.*

data class ResponseExpenseDto(
    val id: UUID?,
    val name: String,
    val amount: Long,
    val note: String,
    val dateTime: LocalDateTime,
    val employee: ResponseEmployeeDto
)

data class RequestExpenseDto(
    @field:NotBlank(message = "Employee ID cannot be blank")
    val employeeId: String,

    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:PositiveOrZero(message = "Amount must be positive or zero")
    val amount: Long,

    val note: String = ""
)