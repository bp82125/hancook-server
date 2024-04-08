package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestCustomerOrderDto
import com.hancook.hancookbe.dtos.ResponseCustomerOrderDto
import com.hancook.hancookbe.models.*
import com.hancook.hancookbe.models.compositeKeys.InvoiceDetailId
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

fun CustomerOrder.toInvoice(employee: Employee, customerPayment: Long): Invoice {
    val invoice = Invoice(
        id = null,
        createdTime = LocalDateTime.now(),
        customerPayment = customerPayment,
        employee = employee,
        table = this.table
    )

    val invoiceDetails = this.details.map { orderDetail ->
        InvoiceDetail(
            id = InvoiceDetailId(invoice, orderDetail.id.dish),
            unitPrice = orderDetail.id.dish.price,
            quantity = orderDetail.quantity,
            note = orderDetail.note
        )
    }
    invoice.details = invoiceDetails
    return invoice
}