package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestPositionDto
import com.hancook.hancookbe.dtos.ResponsePositionDto
import com.hancook.hancookbe.exceptions.DeleteAdminAccountPositionException
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.PositionRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class PositionService(
    @Autowired private val positionRepository: PositionRepository,
    @Autowired private val employeeService: EmployeeService
) {
    fun getAllPosition(): List<ResponsePositionDto>{
        return positionRepository.findAllByDeletedFalse().map { it.toResponse() }
    }

    fun getPositionById(id: UUID): ResponsePositionDto {
        return positionRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = id.toString()) }
    }

    fun createPosition(requestPosition: RequestPositionDto): ResponsePositionDto{
        val position = requestPosition.toEntity()
        val createdPosition = positionRepository.save(position)

        return createdPosition.toResponse()
    }

    fun updatePosition(id: UUID, requestPosition: RequestPositionDto): ResponsePositionDto {
        val position = requestPosition.toEntity(id = id)
        val updatedPosition = positionRepository
            .findById(id)
            .map { positionRepository.save(position) }
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = id.toString()) }

        return updatedPosition.toResponse()
    }

    fun deletePosition(id: UUID) {
        val position = positionRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = id.toString()) }

        if (position.employees.any { it.account?.isAdmin() == true }){
            throw DeleteAdminAccountPositionException(position.positionName)
        }

        position.employees.forEach { it.id?.let { it1 -> employeeService.deleteEmployee(it1) } }
        position.deleted = !position.deleted
        positionRepository.save(position)
    }
}