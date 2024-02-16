package com.hancook.hancookbe.dtos

import jakarta.validation.constraints.NotEmpty
import java.util.UUID

data class RequestDishTypeDto(
    @field:NotEmpty(message = "Name for dish type is required.")
    val dishTypeName: String,
)

data class ResponseDishTypeDto(
    val id: UUID?,
    val dishTypeName: String,
    val numberOfDishes: Int
)