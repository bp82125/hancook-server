package com.hancook.hancookbe.dtos

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotEmpty
import java.util.*

data class RequestPositionDto(
    @field:NotEmpty(message = "Name for position is required")
    val positionName: String,

    @field:DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than 0")
    val salaryCoefficient: Double
)

data class ResponsePositionDto(
    val id: UUID?,
    val positionName: String,
    val salaryCoefficient: Double,
    val numberOfEmployees: Int
)