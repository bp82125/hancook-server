package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestOrderDetailDto
import com.hancook.hancookbe.dtos.ResponseOrderDetailDto
import com.hancook.hancookbe.models.Dish
import com.hancook.hancookbe.models.CustomerOrder
import com.hancook.hancookbe.models.CustomerOrderDetail
import com.hancook.hancookbe.models.compositeKeys.CustomerOrderDetailId

fun RequestOrderDetailDto.toEntity(customerOrder: CustomerOrder, dish: Dish): CustomerOrderDetail {
    return CustomerOrderDetail(
        CustomerOrderDetailId(customerOrder, dish),
        quantity = quantity,
        note = note ?: ""
    )
}

fun CustomerOrderDetail.toResponse(): ResponseOrderDetailDto {
    return ResponseOrderDetailDto(
        dish = this.id.dish.toResponse(),
        quantity = this.quantity,
        note = this.note
    )
}