package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestExpenseDto
import com.hancook.hancookbe.dtos.ResponseExpenseDto
import com.hancook.hancookbe.models.Employee
import com.hancook.hancookbe.models.Expense
import java.time.LocalDateTime
import java.util.*

fun Expense.toResponse(): ResponseExpenseDto {
    return ResponseExpenseDto(
        id = this.id,
        name = this.name,
        amount = this.amount,
        note = this.note,
        dateTime = this.dateTime,
        employee = this.employee.toResponse()
    )
}

fun RequestExpenseDto.toExpense(id: UUID? = null, employee: Employee): Expense {
    return Expense(
        id = id,
        name = this.name,
        amount = this.amount,
        note = this.note,
        dateTime = LocalDateTime.now(),
        employee = employee
    )
}