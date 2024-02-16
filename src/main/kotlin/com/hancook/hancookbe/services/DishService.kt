package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestDishDto
import com.hancook.hancookbe.dtos.ResponseDishDto
import com.hancook.hancookbe.repositoríe.DishRepository
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositoríe.DishTypeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID


@Service
@Transactional
class DishService(
    @Autowired private val dishRepository: DishRepository,
    @Autowired private val dishTypeRepository: DishTypeRepository
) {

    fun getAllDishes(): List<ResponseDishDto> {
        return dishRepository.findAll().map { it.toResponse() }
    }

    fun getDishById(id: UUID): ResponseDishDto {
        return dishRepository
            .findById(id)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Dish", id = id) }
    }

    fun createDish(requestDish: RequestDishDto): ResponseDishDto {
        val dishType = dishTypeRepository
            .findById(requestDish.dishTypeId)
            .orElseThrow { ElementNotFoundException(objectName = "Dish type", id = requestDish.dishTypeId) }

        val dish = requestDish.toEntity(dishType = dishType)
        val savedDish = dishRepository.save(dish)
        return savedDish.toResponse()
    }

    fun updateDish(id: UUID, requestDish: RequestDishDto): ResponseDishDto {
        val dishType = dishTypeRepository
            .findById(requestDish.dishTypeId)
            .orElseThrow { ElementNotFoundException(objectName = "Dish type", id = requestDish.dishTypeId) }

        val dish = requestDish.toEntity(id = id, dishType = dishType)
        val updatedDish = dishRepository
            .findById(id)
            .map { dishRepository.save(dish) }
            .orElseThrow { ElementNotFoundException(objectName = "Dish", id = id) }

        return updatedDish.toResponse()
    }

    fun deleteDish(id: UUID) {
        dishRepository
            .findById(id)
            .map { dishRepository.deleteById(id) }
            .orElseThrow { ElementNotFoundException(objectName = "Dish", id = id) }
    }
}