package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestExpenseDto
import com.hancook.hancookbe.dtos.ResponseExpenseDto
import com.hancook.hancookbe.models.Expense
import java.time.LocalDateTime
import java.util.*

fun Expense.toResponse(): ResponseExpenseDto {
    return ResponseExpenseDto(
        id = this.id,
        name = this.name,
        amount = this.amount,
        note = this.note,
        dateTime = this.dateTime
    )
}

fun RequestExpenseDto.toExpense(id: UUID? = null): Expense {
    return Expense(
        id = id,
        name = this.name,
        amount = this.amount,
        note = this.note,
        dateTime = LocalDateTime.now()
    )
}