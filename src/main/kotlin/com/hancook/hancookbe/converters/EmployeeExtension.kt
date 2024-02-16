package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestEmployeeDto
import com.hancook.hancookbe.dtos.ResponseEmployeeDto
import com.hancook.hancookbe.models.Account
import com.hancook.hancookbe.models.Employee
import com.hancook.hancookbe.models.Position
import java.util.*

fun RequestEmployeeDto.toEntity(id: UUID? = null, position: Position, account: Account? = null): Employee {
    return Employee(
        id = id,
        name = this.name,
        gender = this.gender,
        address = this.address,
        phoneNumber = this.phoneNumber,
        position = position,
        account = account
    )
}

fun Employee.toResponse(): ResponseEmployeeDto {
    return ResponseEmployeeDto(
        id = this.id ?: error("Employee id cannot be null"),
        name = this.name,
        gender = this.gender,
        address = this.address,
        phoneNumber = this.phoneNumber,
        position = this.position.toResponse(),
        account = this.account?.toResponse() // Convert account if it exists
    )
}