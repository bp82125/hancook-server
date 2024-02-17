package com.hancook.hancookbe.system

import com.hancook.hancookbe.dtos.RequestAccountDto
import com.hancook.hancookbe.enums.Role
import com.hancook.hancookbe.services.AccountService
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AdminUserInitializer(
    private val accountService: AccountService,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Check if the admin user already exists
        if (accountService.findAllAccounts().isEmpty()) {
            // Admin user doesn't exist, create it
            val adminAccount = RequestAccountDto(
                username = "admin",
                password = "adminPassword", // You should hash this password using the password encoder
                role = Role.ADMIN, // Assuming you have a Role enum with ADMIN role defined
                enabled = true
            )

            // Save admin user
            accountService.createAccount(adminAccount)
            println("Admin user created successfully")
        } else {
            println("Admin user already exists")
        }
    }
}