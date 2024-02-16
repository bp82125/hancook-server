package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestPositionDto
import com.hancook.hancookbe.dtos.ResponsePositionDto
import com.hancook.hancookbe.models.Position
import java.util.*

fun Position.toResponse(): ResponsePositionDto {
    return ResponsePositionDto(
        id = this.id,
        positionName = this.positionName,
        salaryCoefficient = this.salaryCoefficient,
        numberOfEmployees = this.getNumberOfEmployees()
    )
}

fun RequestPositionDto.toEntity(id: UUID? = null): Position {
    return Position(
        id = id,
        positionName = this.positionName,
        salaryCoefficient = this.salaryCoefficient,
        employees = mutableListOf()
    )
}