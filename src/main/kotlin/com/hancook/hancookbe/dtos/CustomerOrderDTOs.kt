package com.hancook.hancookbe.dtos

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.UUID

data class RequestCustomerOrderDto(
    @field:NotNull(message = "Employee ID must not be null")
    val employeeId: UUID
)

data class ResponseCustomerOrderDto(
    val id: UUID?,
    val employee: ResponseEmployeeDto,
    val table: ResponseTableDto?,
    val placedTime: LocalDateTime,
    val details: List<ResponseOrderDetailDto>,
)