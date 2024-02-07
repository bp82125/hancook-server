package com.hancook.hancookbe.service

import com.hancook.hancookbe.converter.toEntity
import com.hancook.hancookbe.dto.RequestDishTypeDto
import com.hancook.hancookbe.model.DishType
import com.hancook.hancookbe.repository.DishTypeRepository
import com.hancook.hancookbe.exception.DishTypeNotFoundException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class DishTypeService (@Autowired private val dishTypeRepository: DishTypeRepository) {
    fun getAllDishTypes(): List<DishType> {
        return dishTypeRepository.findAll()
    }

    fun getDishTypeById(id: UUID): DishType {
        return dishTypeRepository
            .findById(id)
            .orElseThrow { DishTypeNotFoundException("Dish type with id $id not found") }
    }

    fun createDishType(dishTypeDto: RequestDishTypeDto): DishType {
        val dishType = dishTypeDto.toEntity()
        return dishTypeRepository.save(dishType)
    }

    fun updateDishType(id: UUID, dishTypeDto: RequestDishTypeDto): DishType {
        val updatedDishType = dishTypeDto.toEntity().apply { this.id = id }
        return dishTypeRepository
            .findById(id)
            .map { dishTypeRepository.save(updatedDishType) }
            .orElseThrow{ DishTypeNotFoundException("Dish type with id $id not found") }
    }

    fun deleteDishType(id: UUID): Boolean {
        return if (dishTypeRepository.existsById(id)) {
            dishTypeRepository.deleteById(id)
            true // Deletion successful
        } else {
            false // Dish with the given ID not found
        }
    }
}