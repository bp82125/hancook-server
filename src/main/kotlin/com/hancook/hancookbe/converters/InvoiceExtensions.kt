package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.ResponseInvoice
import com.hancook.hancookbe.models.Invoice

fun Invoice.toResponse(): ResponseInvoice {
    return ResponseInvoice(
        id = this.id,
        employee = this.employee!!.toResponse(),
        table = this.table!!.toResponse(),
        createdTime = this.createdTime,
        customerPayment = this.customerPayment,
        totalPrice = this.calculateTotalPrice(),
    )
}