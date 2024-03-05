package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.AccountPrinciple
import com.hancook.hancookbe.dtos.CreateAccountDto
import com.hancook.hancookbe.dtos.ResponseAccountDto
import com.hancook.hancookbe.models.Account
import com.hancook.hancookbe.models.Employee
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

fun CreateAccountDto.toEntity(id: UUID? = null, employee: Employee? = null): Account {
    return Account(
        id = id,
        username = this.username,
        password = this.password,
        enabled = this.enabled,
        role = this.role,
        employee = employee
    )
}

fun Account.toResponse(): ResponseAccountDto {
    return ResponseAccountDto(
        id = this.id,
        username = this.username,
        enabled = this.enabled,
        role = this.role,
        employeeId = this.employee?.id,
        employeeName = this.employee?.name
    )
}

fun Account.toUserDetails(): UserDetails {
    return AccountPrinciple(this)
}