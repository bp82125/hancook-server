package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestDishTypeDto
import com.hancook.hancookbe.dtos.ResponseDishTypeDto
import com.hancook.hancookbe.exceptions.AssociatedEntitiesException
import com.hancook.hancookbe.repositories.DishTypeRepository
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class DishTypeService (
    @Autowired private val dishTypeRepository: DishTypeRepository
) {
    fun getAllDishTypes(): List<ResponseDishTypeDto> {
        return dishTypeRepository.findAllByDeletedFalse().map { it.toResponse() }
    }

    fun getDishTypeById(id: UUID): ResponseDishTypeDto {
        return dishTypeRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Dish type", id = id.toString()) }
    }

    fun createDishType(requestDishType: RequestDishTypeDto): ResponseDishTypeDto {
        val dishType = requestDishType.toEntity()
        val savedDishType = dishTypeRepository.save(dishType)

        return savedDishType.toResponse()
    }

    fun updateDishType(id: UUID, requestDishType: RequestDishTypeDto): ResponseDishTypeDto {
        val dishType = requestDishType.toEntity(id = id)
        val updatedDishType = dishTypeRepository
            .findById(id)
            .map { dishTypeRepository.save(dishType) }
            .orElseThrow{ ElementNotFoundException(objectName = "Dish type", id = id.toString()) }

        return updatedDishType.toResponse()
    }

    fun deleteDishType(id: UUID) {
        val dishType = dishTypeRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Dish type", id = id.toString()) }

        val nonDeletedDishes = dishType.dishes.filter { !it.deleted }

        if (nonDeletedDishes.isEmpty()) {
            // Soft delete the DishType and associated Dishes
            dishType.deleted = true
            dishType.dishes.forEach { it.deleted = true }

            dishTypeRepository.save(dishType)
        } else {
            throw AssociatedEntitiesException("Dish type", "Dish")
        }
    }
}