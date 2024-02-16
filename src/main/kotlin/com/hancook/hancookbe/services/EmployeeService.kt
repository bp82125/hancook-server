package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestEmployeeDto
import com.hancook.hancookbe.dtos.ResponseEmployeeDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositoríe.AccountRepository
import com.hancook.hancookbe.repositoríe.EmployeeRepository
import com.hancook.hancookbe.repositoríe.PositionRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class EmployeeService(
    @Autowired private val employeeRepository: EmployeeRepository,
    @Autowired private val positionRepository: PositionRepository,
    @Autowired private val accountRepository: AccountRepository
) {
    fun getAllEmployees(): List<ResponseEmployeeDto>{
        return employeeRepository.findAll().map { it.toResponse() }
    }

    fun getEmployeeById(id: UUID): ResponseEmployeeDto {
        return employeeRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = id) }
    }

    fun createEmployee(requestEmployee: RequestEmployeeDto): ResponseEmployeeDto {
        val position = positionRepository
            .findById(requestEmployee.positionId)
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = requestEmployee.positionId) }

        val account = requestEmployee.accountId?.let { accountId ->
            accountRepository.findById(accountId)
                .orElseThrow { ElementNotFoundException(objectName = "Account", id = requestEmployee.accountId) }
        }

        val employee = requestEmployee.toEntity(position = position, account = account)
        val createdEmployee = employeeRepository.save(employee)

        return createdEmployee.toResponse()
    }

    fun updateEmployee(id: UUID, requestEmployee: RequestEmployeeDto): ResponseEmployeeDto {
        val position = positionRepository
            .findById(requestEmployee.positionId)
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = requestEmployee.positionId) }

        val account = requestEmployee.accountId?.let { accountId ->
            accountRepository.findById(accountId)
                .orElseThrow { ElementNotFoundException(objectName = "Account", id = requestEmployee.accountId) }
        }

        val employee = requestEmployee.toEntity(id = id, position = position, account = account)
        val updatedEmployee = employeeRepository.save(employee)

        return updatedEmployee.toResponse()
    }

    fun deleteEmployee(id: UUID) {
        employeeRepository
            .findById(id)
            .map { employeeRepository.deleteById(id) }
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = id) }
    }
}