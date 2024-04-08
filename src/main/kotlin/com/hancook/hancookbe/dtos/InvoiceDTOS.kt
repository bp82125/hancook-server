package com.hancook.hancookbe.dtos

import java.time.LocalDateTime
import java.util.*

data class RequestInvoice(
    val employeeId: UUID,
    val customerPayment: Long
)

data class ResponseInvoice(
    val id: UUID?,
    val employee: ResponseEmployeeDto,
    val table: ResponseTableDto?,
    val createdTime: LocalDateTime,
    val customerPayment: Long,
    val totalPrice: Long,
)