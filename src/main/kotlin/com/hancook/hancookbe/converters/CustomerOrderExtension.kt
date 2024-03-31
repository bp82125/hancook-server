package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestCustomerOrderDto
import com.hancook.hancookbe.dtos.ResponseCustomerOrderDto
import com.hancook.hancookbe.models.Employee
import com.hancook.hancookbe.models.CustomerOrder
import com.hancook.hancookbe.models.Table
import java.time.LocalDateTime
import java.util.UUID

fun RequestCustomerOrderDto.toEntity(
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

fun CustomerOrder.toResponse(): ResponseCustomerOrderDto {
    return ResponseCustomerOrderDto(
        id = this.id,
        employee = this.employee.toResponse(),
        table = this.table?.toResponse(),
        details = this.details.map { it.toResponse() },
        placedTime = this.orderPlacedTime
    )
}