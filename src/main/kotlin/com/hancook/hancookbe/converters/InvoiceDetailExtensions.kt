package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.ResponseInvoiceDetail
import com.hancook.hancookbe.models.InvoiceDetail

fun InvoiceDetail.toResponse(): ResponseInvoiceDetail {
    return ResponseInvoiceDetail(
        dish = this.id.dish.toResponse(),
        unitPrice = this.unitPrice,
        quantity = this.quantity,
        note = this.note
    )
}