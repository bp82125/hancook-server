package com.hancook.hancookbe.system

import com.hancook.hancookbe.dtos.CreateAccountDto
import com.hancook.hancookbe.dtos.RequestEmployeeDto
import com.hancook.hancookbe.dtos.RequestPositionDto
import com.hancook.hancookbe.enums.Gender
import com.hancook.hancookbe.enums.Role
import com.hancook.hancookbe.services.AccountService
import com.hancook.hancookbe.services.EmployeeService
import com.hancook.hancookbe.services.PositionService
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AdminUserInitializer(
    private val accountService: AccountService,
    private val employeeService: EmployeeService,
    private val positionService: PositionService,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Check if the admin user already exists
        if (!accountService.adminAccountExist()) {

            val adminPosition = RequestPositionDto(
                positionName = "Quản trị hệ thống",
                salaryCoefficient = 100.0
            )

            val createdAdminPosition = positionService.createPosition(adminPosition)

            val adminEmployee = createdAdminPosition.id?.let {
                RequestEmployeeDto(
                    name = "Administrator",
                    gender = Gender.OTHER,
                    address = "123 Main Street, Anytown, AnyCountry",
                    phoneNumber = "0000000000",
                    positionId = it,
                    accountId = null
                )
            }

            val createdAdminEmployee = adminEmployee?.let { employeeService.createEmployee(it) }

            // Admin user doesn't exist, create it
            val adminAccount = CreateAccountDto(
                username = "admin",
                password = "adminPassword123",
                role = Role.ADMIN,
                enabled = true,
                employeeId = createdAdminEmployee?.id
            )
            accountService.createAccount(adminAccount)
            println("Admin user created successfully")
        } else {
            println("Admin user already exists")
        }
    }
}