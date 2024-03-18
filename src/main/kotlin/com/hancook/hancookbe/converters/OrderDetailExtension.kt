package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestOrderDetailDto
import com.hancook.hancookbe.dtos.ResponseOrderDetailDto
import com.hancook.hancookbe.models.Dish
import com.hancook.hancookbe.models.CustomerOrder
import com.hancook.hancookbe.models.OrderDetail
import com.hancook.hancookbe.models.compositeKeys.OrderDetailId

fun RequestOrderDetailDto.toEntity(customerOrder: CustomerOrder, dish: Dish): OrderDetail {
    return OrderDetail(
        OrderDetailId(customerOrder, dish),
        quantity = quantity,
        note = note ?: ""
    )
}

fun OrderDetail.toResponse(): ResponseOrderDetailDto {
    return ResponseOrderDetailDto(
        dish = this.id.dish.toResponse(),
        quantity = this.quantity,
        note = this.note
    )
}