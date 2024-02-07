package com.hancook.hancookbe.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.util.UUID

data class RequestDishDto(
    @field:NotEmpty(message = "Name for the dish is required.")
    val dishName: String,

    @field:Positive(message = "Price must be a positive number.")
    val price: Long,

    @field:NotEmpty(message = "Image for the dish is required.")
    val imagePath: String,

    @field:NotNull(message = "Dish type for the dish is required.")
    val dishTypeId: UUID
)
data class ResponseDishDto(
    val id: UUID?,
    val dishName: String,
    val price: Long,
    val imagePath: String,
    val dishType: ResponseDishTypeDto
)