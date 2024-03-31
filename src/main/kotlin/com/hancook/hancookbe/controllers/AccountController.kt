package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.CreateAccountDto
import com.hancook.hancookbe.dtos.ResponseAccountDto
import com.hancook.hancookbe.dtos.UpdateAccountDto
import com.hancook.hancookbe.dtos.UpdatePasswordDto
import com.hancook.hancookbe.services.AccountService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("\${api.endpoint.base-url}/accounts")
class AccountController(
    @Autowired private val accountService: AccountService,
) {

    @GetMapping("", "/")
    fun getAllAccounts(): ResponseEntity<ApiResponse<List<ResponseAccountDto>>>{
        val accounts = accountService.findAllAccounts()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = accounts,
                message = "Found accounts"
            )
        )
    }

    @GetMapping("/{id}")
    fun getAccountById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponseAccountDto>>{
        val account = accountService.findAccountById(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Found account with ID: $id"
            )
        )
    }

    @PostMapping
    fun createAccount(
        @Valid @RequestBody requestAccount: CreateAccountDto
    ): ResponseEntity<ApiResponse<ResponseAccountDto>> {
        val account = accountService.createAccount(requestAccount)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = account,
                    message = "Successfully created an account"
                )
            )
    }

    @PutMapping("/{id}")
    fun updateAccount(
        @PathVariable id: UUID,
        @Valid @RequestBody requestAccount: UpdateAccountDto
    ): ResponseEntity<ApiResponse<ResponseAccountDto>> {
        val account = accountService.updateAccount(id, requestAccount)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Updated success"
            )
        )
    }

    @PutMapping("/toggle/{id}")
    fun toggleAccount(
        @PathVariable id: UUID,
    ): ResponseEntity<ApiResponse<ResponseAccountDto>> {
        val account = accountService.toggleAccount(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Toggled success"
            )
        )
    }

    @PutMapping("/changePassword/{id}")
    fun changePasswordAccount(
        @PathVariable id: UUID,
        @Valid @RequestBody requestPassword: UpdatePasswordDto
    ): ResponseEntity<ApiResponse<ResponseAccountDto>> {
        val account = accountService.changePasswordAccount(id, requestPassword)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = account,
                message = "Updated password success"
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteAccount(
        @PathVariable id: UUID,
    ): ResponseEntity<ApiResponse<Unit>> {
        accountService.deleteAccount(id)
        return ResponseEntity.ok(ApiResponse(success = true, statusCode = HttpStatus.OK.value(), message = "Account has been deleted successfully"))
    }
}