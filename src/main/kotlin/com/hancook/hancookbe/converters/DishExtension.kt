package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestDishDto
import com.hancook.hancookbe.dtos.ResponseDishCountDto
import com.hancook.hancookbe.dtos.ResponseDishDto
import com.hancook.hancookbe.models.Dish
import com.hancook.hancookbe.models.DishType
import java.util.*


fun Dish.toResponse() : ResponseDishDto {
    return ResponseDishDto(
        id = this.id,
        dishName = this.dishName,
        price = this.price,
        imagePath = this.imagePath,
        dishType = this.dishType.toResponse()
    )
}

fun Dish.toCount(count: Long): ResponseDishCountDto {
    return ResponseDishCountDto(
        id = this.id,
        name = this.dishName,
        count = count
    )
}

fun RequestDishDto.toEntity(id: UUID? = null, dishType: DishType) : Dish {
    return Dish(
        id = id,
        dishName = this.dishName,
        price = this.price,
        imagePath = this.imagePath,
        dishType = dishType
    )
}
