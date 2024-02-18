package com.hancook.hancookbe.security

import com.hancook.hancookbe.system.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.endpoint.base-url}/accounts")
class AuthController(
    @Autowired private val authService: AuthService
) {

    private val LOGGER: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun getLoginInfo(authentication: Authentication): ApiResponse<Any> {
        LOGGER.debug("Authenticated user: ${authentication.name}")
        return ApiResponse(
            success = true,
            statusCode = HttpStatus.OK.value(),
            data = authService.createLoginInfo(authentication),
            message = "User info and JWT"
        )
    }
}