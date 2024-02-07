package com.hancook.hancookbe.converter

import com.hancook.hancookbe.dto.RequestDishTypeDto
import com.hancook.hancookbe.dto.ResponseDishTypeDto
import com.hancook.hancookbe.model.DishType

fun DishType.toResponse(): ResponseDishTypeDto {
    return ResponseDishTypeDto(
        id = this.id,
        dishTypeName = this.dishTypeName,
        numberOfDishes = this.getNumberOfDishes()
    )
}

fun RequestDishTypeDto.toEntity() : DishType {
    return DishType(
        dishTypeName = this.dishTypeName,
        dishes = mutableListOf()
    )
}