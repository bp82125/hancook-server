package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestAccountDto
import com.hancook.hancookbe.dtos.ResponseAccountDto
import com.hancook.hancookbe.services.AccountService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
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
        return ResponseEntity.ok(ApiResponse(success = true, statusCode = HttpStatus.OK.value() ,data = accounts, message = "Found accounts"))
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
        @Valid @RequestBody requestAccount: RequestAccountDto
    ): ResponseEntity<ApiResponse<ResponseAccountDto>> {
        val account = accountService.createAccount(requestAccount)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = account,
                    message = "Successfully created a dish"
                )
            )
    }

    @PutMapping("/{id}")
    fun updateAccount(
        @PathVariable id: UUID,
        @Valid @RequestBody requestAccount: RequestAccountDto
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

    @DeleteMapping("/{id}")
    fun deleteAccount(
        @PathVariable id: UUID,
    ): ResponseEntity<ApiResponse<Unit>> {
        accountService.deleteAccount(id)
        return ResponseEntity.ok(ApiResponse(success = true, statusCode = HttpStatus.OK.value(), message = "Account has been deleted successfully"))
    }
}