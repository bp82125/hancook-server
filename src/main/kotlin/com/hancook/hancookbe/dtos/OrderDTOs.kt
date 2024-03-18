package com.hancook.hancookbe.dtos

import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.UUID

data class RequestOrderDto(
    @field:NotNull(message = "Table ID must not be null")
    val tableId: UUID,

    @field:NotNull(message = "Employee ID must not be null")
    val employeeId: UUID
)

data class ResponseOrderDto(
    val id: UUID?,
    val employee: ResponseEmployeeDto,
    val table: ResponseTableDto?,
    val placedTime: LocalDateTime,
    val details: List<ResponseOrderDetailDto>,
)