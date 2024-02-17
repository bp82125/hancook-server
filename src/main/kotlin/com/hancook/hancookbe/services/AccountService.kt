package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.converters.toUserDetails
import com.hancook.hancookbe.dtos.RequestAccountDto
import com.hancook.hancookbe.dtos.ResponseAccountDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.AccountRepository
import com.hancook.hancookbe.repositories.EmployeeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class AccountService (
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val employeeRepository: EmployeeRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
): UserDetailsService {
    fun findAllAccounts(): List<ResponseAccountDto> {
        return accountRepository.findAll().map { it.toResponse() }
    }

    fun findAccountById(id: UUID): ResponseAccountDto {
        return accountRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Account", id = id) }
    }

    fun createAccount(requestAccount: RequestAccountDto): ResponseAccountDto {
        val employee = requestAccount.employeeId?.let {
            employeeRepository
                .findById(it)
                .orElseThrow { ElementNotFoundException(objectName = "Employee", id = requestAccount.employeeId) }
        }

        val account = requestAccount.toEntity(employee = employee).apply { this.password = passwordEncoder.encode(this.password) }
        val createdAccount = accountRepository.save(account)
        return createdAccount.toResponse()
    }

    fun updateAccount(id: UUID, requestAccount: RequestAccountDto): ResponseAccountDto{
        val employee = requestAccount.employeeId?.let {
            employeeRepository
                .findById(it)
                .orElseThrow { ElementNotFoundException(objectName = "Employee", id = requestAccount.employeeId) }
        }

        val account = requestAccount.toEntity(id = id ,employee = employee).apply { this.password = passwordEncoder.encode(this.password) }
        val updatedAccount = accountRepository.save(account)
        return updatedAccount.toResponse()
    }

    fun deleteAccount(id: UUID){
        return accountRepository
            .findById(id)
            .map { accountRepository.deleteById(id) }
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = id) }
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository
            .findByUsername(username)
            .map { it.toUserDetails() }
            .orElseThrow { UsernameNotFoundException("Username $username is not found") }
    }
}