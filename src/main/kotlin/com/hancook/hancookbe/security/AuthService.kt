package com.hancook.hancookbe.security

import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.AccountPrinciple
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Objects

@Service
class AuthService(
    @Autowired private val jwtProvider: JwtProvider
) {
    fun createLoginInfo(authentication: Authentication): Map<String, Any> {
        val accountPrincipal = authentication.principal as AccountPrinciple
        val account = accountPrincipal.getAccount()
        val responseAccount = account.toResponse()

        val token = jwtProvider.createToken(authentication)

        return mapOf(
            "accountInfo" to responseAccount,
            "token" to token
        )

    }

}