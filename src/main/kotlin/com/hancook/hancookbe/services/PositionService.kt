package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestPositionDto
import com.hancook.hancookbe.dtos.ResponsePositionDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.PositionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class PositionService(private val positionRepository: PositionRepository) {
    fun getAllPosition(): List<ResponsePositionDto>{
        return positionRepository.findAll().map { it.toResponse() }
    }

    fun getPositionById(id: UUID): ResponsePositionDto {
        return positionRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = id) }
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
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = id) }

        return updatedPosition.toResponse()
    }

    fun deletePosition(id: UUID) {
        positionRepository
            .findById(id)
            .map { positionRepository.deleteById(id) }
            .orElseThrow { ElementNotFoundException(objectName = "Position", id = id) }
    }
}