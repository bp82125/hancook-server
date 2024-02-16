package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestDishTypeDto
import com.hancook.hancookbe.dtos.ResponseDishTypeDto
import com.hancook.hancookbe.models.DishType
import java.util.*

fun DishType.toResponse(): ResponseDishTypeDto {
    return ResponseDishTypeDto(
        id = this.id,
        dishTypeName = this.dishTypeName,
        numberOfDishes = this.getNumberOfDishes()
    )
}

fun RequestDishTypeDto.toEntity(id: UUID? = null) : DishType {
    return DishType(
        id = id,
        dishTypeName = this.dishTypeName,
        dishes = mutableListOf()
    )
}