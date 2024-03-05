package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.converters.toUserDetails
import com.hancook.hancookbe.dtos.CreateAccountDto
import com.hancook.hancookbe.dtos.ResponseAccountDto
import com.hancook.hancookbe.dtos.UpdateAccountDto
import com.hancook.hancookbe.dtos.UpdatePasswordDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.exceptions.InvalidPasswordException
import com.hancook.hancookbe.exceptions.UsernameAlreadyExistsException
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
        return accountRepository
            .findAll()
            .map { it.toResponse() }
    }

    fun findAccountById(id: UUID): ResponseAccountDto {
        return accountRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Account", id = id) }
    }

    fun createAccount(requestAccount: CreateAccountDto): ResponseAccountDto {
        val employee = requestAccount.employeeId?.let {
            employeeRepository
                .findById(it)
                .orElseThrow { ElementNotFoundException(objectName = "Employee", id = requestAccount.employeeId) }
        }


        if (accountRepository.existsByUsername(requestAccount.username)) {
            throw UsernameAlreadyExistsException("Username '${requestAccount.username}' is already taken.")
        }

        val account = requestAccount
            .toEntity(employee = employee)
            .apply { this.password = passwordEncoder.encode(this.password) }

        employee
            ?.apply { this.account = account }
            ?.let { employeeRepository.save(it) }

        val createdAccount = accountRepository.save(account)
        return createdAccount.toResponse()
    }

    fun updateAccount(id: UUID, updateAccount: UpdateAccountDto): ResponseAccountDto{
        val account = accountRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Account", id = id) }
            .apply { this.role = updateAccount.role }

        val updatedAccount = accountRepository.save(account)
        return updatedAccount.toResponse()
    }

    fun changePasswordAccount(id: UUID, updatePasswordDto: UpdatePasswordDto): ResponseAccountDto{
        val account = accountRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Account", id = id) }

        if (!passwordEncoder.matches(updatePasswordDto.oldPassword, account.password)) {
            throw InvalidPasswordException("Invalid current password")
        }

        account.password = passwordEncoder.encode(updatePasswordDto.newPassword)
        return accountRepository.save(account).toResponse()
    }

    fun deleteAccount(id: UUID) {
        val account = accountRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Account", id = id) }

        account.removeEmployee()
        accountRepository.deleteById(id)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository
            .findByUsername(username)
            .map { it.toUserDetails() }
            .orElseThrow { UsernameNotFoundException("Username $username is not found") }
    }

    fun toggleAccount(id: UUID): ResponseAccountDto {
        return accountRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = id) }
            .apply { this.enabled = !this.enabled }
            .let { accountRepository.save(it) }
            .toResponse()
    }
}