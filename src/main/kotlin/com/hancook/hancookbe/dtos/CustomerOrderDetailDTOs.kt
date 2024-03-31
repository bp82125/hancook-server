package com.hancook.hancookbe.dtos

data class RequestOrderDetailDto(
    val quantity: Int,
    val note: String? = null
)

data class ResponseOrderDetailDto(
    val dish: ResponseDishDto,
    val quantity: Int,
    val note: String?
)