package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestAccountDto
import com.hancook.hancookbe.dtos.ResponseAccountDto
import com.hancook.hancookbe.models.Account
import com.hancook.hancookbe.models.Employee
import java.util.*

fun RequestAccountDto.toEntity(id: UUID? = null, employee: Employee): Account {
    return Account(
        id = id,
        username = this.username,
        password = this.password,
        employee = employee
    )
}

fun Account.toResponse(): ResponseAccountDto {
    return ResponseAccountDto(
        id = this.id,
        username = this.username,
    )
}