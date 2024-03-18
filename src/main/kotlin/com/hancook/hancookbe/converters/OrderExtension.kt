package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestOrderDto
import com.hancook.hancookbe.dtos.ResponseOrderDto
import com.hancook.hancookbe.models.Employee
import com.hancook.hancookbe.models.CustomerOrder
import com.hancook.hancookbe.models.Table
import java.time.LocalDateTime
import java.util.UUID

fun RequestOrderDto.toEntity(
    id: UUID? = null,
    table: Table,
    employee: Employee,
    placedTime: LocalDateTime? = null
): CustomerOrder {
    return CustomerOrder(
        id = id,
        table = table,
        employee = employee,
        orderPlacedTime = placedTime ?: LocalDateTime.now()
    )
}

fun CustomerOrder.toResponse(): ResponseOrderDto {
    return ResponseOrderDto(
        id = this.id,
        employee = this.employee.toResponse(),
        table = this.table?.toResponse(),
        details = this.details.map { it.toResponse() },
        placedTime = this.orderPlacedTime
    )
}