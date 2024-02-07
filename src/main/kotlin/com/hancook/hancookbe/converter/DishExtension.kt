package com.hancook.hancookbe.converter

import com.hancook.hancookbe.dto.RequestDishDto
import com.hancook.hancookbe.dto.ResponseDishDto
import com.hancook.hancookbe.model.Dish
import com.hancook.hancookbe.model.DishType


fun Dish.toResponse() : ResponseDishDto {
    return ResponseDishDto(
        id = this.id,
        dishName = this.dishName,
        price = this.price,
        imagePath = this.imagePath,
        dishType = this.dishType.toResponse()
    )
}

fun RequestDishDto.toEntity(dishType: DishType) : Dish {
    return Dish(
        dishName = this.dishName,
        price = this.price,
        imagePath = this.imagePath,
        dishType = dishType
    )
}