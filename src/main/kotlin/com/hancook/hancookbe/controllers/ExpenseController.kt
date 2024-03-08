package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestExpenseDto
import com.hancook.hancookbe.dtos.ResponseExpenseDto
import com.hancook.hancookbe.services.ExpenseService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/expenses")
class ExpenseController(
    @Autowired private val expenseService: ExpenseService
) {
    @GetMapping("", "/")
    fun getAllExpense(): ResponseEntity<ApiResponse<List<ResponseExpenseDto>>> {
        val expenses = expenseService.getAllExpenses()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = expenses,
                message = "Found expenses"
            )
        )
    }

    @GetMapping("/{id}")
    fun getExpenseById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponseExpenseDto>> {
        val expense = expenseService.getExpenseById(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = expense,
                message = "Found expense with id: $id"
            )
        )
    }

    @PostMapping("", "/")
    fun createExpense(@Valid @RequestBody requestExpenseDto: RequestExpenseDto): ResponseEntity<ApiResponse<ResponseExpenseDto>> {
        val createdExpense = expenseService.createExpense(requestExpenseDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.CREATED.value(),
                data = createdExpense,
                message = "Created an expense"
            )
        )
    }

    @PutMapping("/{id}")
    fun updateExpense(
        @PathVariable id: UUID,
        @Valid @RequestBody requestExpenseDto: RequestExpenseDto
    ): ResponseEntity<ApiResponse<ResponseExpenseDto>> {
        val updatedExpense = expenseService.updateExpense(id, requestExpenseDto)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedExpense,
                message = "Updated an expense with id $id"
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteExpense(
        @PathVariable id: UUID
    ): ResponseEntity<ApiResponse<Unit>> {
        expenseService.deleteExpense(id)
        return ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.OK.value(),
                    message = "Deleted an expense with id $id"
                )
        )
    }

}
