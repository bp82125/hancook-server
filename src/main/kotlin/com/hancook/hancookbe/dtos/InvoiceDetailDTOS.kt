package com.hancook.hancookbe.dtos

data class ResponseInvoiceDetail(
    val dish: ResponseDishDto,
    val unitPrice: Long,
    val quantity: Int,
    val note: String?
)