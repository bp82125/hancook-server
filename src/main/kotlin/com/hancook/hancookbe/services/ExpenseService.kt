package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toExpense
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestExpenseDto
import com.hancook.hancookbe.dtos.ResponseExpenseDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.ExpenseRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class ExpenseService(
    @Autowired private val expenseRepository: ExpenseRepository
) {
    fun getAllExpenses(): List<ResponseExpenseDto>{
        return expenseRepository.findAll().map { it.toResponse() }
    }

    fun getExpenseById(id: UUID): ResponseExpenseDto{
        return expenseRepository
            .findById(id)
            .map{ it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Expense", id = id) }
    }

    fun createExpense(requestExpenseDto: RequestExpenseDto): ResponseExpenseDto {
        val expense = requestExpenseDto.toExpense()
        return expenseRepository.save(expense).toResponse()
    }

    fun updateExpense(id: UUID, requestExpenseDto: RequestExpenseDto): ResponseExpenseDto? {
        val newExpense = requestExpenseDto.toExpense(id)
        val updatedExpense = expenseRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Expense", id = id) }
            .let { expenseRepository.save(newExpense) }

        return updatedExpense.toResponse()
    }

    fun deleteExpense(id: UUID){
        expenseRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Expense", id = id) }
            .let { expenseRepository.deleteById(id) }
    }
}