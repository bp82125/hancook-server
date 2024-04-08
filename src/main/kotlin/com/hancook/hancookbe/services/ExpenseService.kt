package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toExpense
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestExpenseDto
import com.hancook.hancookbe.dtos.ResponseExpenseDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.EmployeeRepository
import com.hancook.hancookbe.repositories.ExpenseRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class ExpenseService(
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val employeeRepository: EmployeeRepository
) {
    fun getAllExpenses(): List<ResponseExpenseDto>{
        return expenseRepository.findAll().map { it.toResponse() }
    }

    fun getExpenseById(id: UUID): ResponseExpenseDto{
        return expenseRepository
            .findById(id)
            .map{ it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Expense", id = id.toString()) }
    }

    fun createExpense(requestExpenseDto: RequestExpenseDto): ResponseExpenseDto {
        val employeeId = UUID.fromString(requestExpenseDto.employeeId)

        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = employeeId.toString()) }

        val expense = requestExpenseDto.toExpense(employee = employee)
        return expenseRepository.save(expense).toResponse()
    }

    fun updateExpense(id: UUID, requestExpenseDto: RequestExpenseDto): ResponseExpenseDto? {
        val oldExpense = expenseRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Expense", id = id.toString()) }

        val employeeId = UUID.fromString(requestExpenseDto.employeeId)

        val employee = employeeRepository
            .findById(employeeId)
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = employeeId.toString()) }

        oldExpense.apply {
            this.employee = employee
            this.name = requestExpenseDto.name
            this.note = requestExpenseDto.note
            this.amount = requestExpenseDto.amount
        }

        val updatedExpense = expenseRepository.save(oldExpense)
        return updatedExpense.toResponse()
    }

    fun deleteExpense(id: UUID){
        expenseRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Expense", id = id.toString()) }
            .let { expenseRepository.deleteById(id) }
    }
}