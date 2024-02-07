package com.hancook.hancookbe.service

import com.hancook.hancookbe.converter.toEntity
import com.hancook.hancookbe.dto.RequestDishDto
import com.hancook.hancookbe.model.Dish
import com.hancook.hancookbe.repository.DishRepository
import com.hancook.hancookbe.exception.DishNotFoundException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID


@Service
@Transactional
class DishService(
    @Autowired private val dishRepository: DishRepository,
    @Autowired private val dishTypeService: DishTypeService
) {

    fun getAllDishes(): List<Dish> {
        return dishRepository.findAll()
    }

    fun getDishById(id: UUID): Dish {
        return dishRepository
            .findById(id)
            .orElseThrow { DishNotFoundException("Dish with id $id not found") }
    }


    fun createDish(dishDto: RequestDishDto): Dish {
        val dishType = dishTypeService.getDishTypeById(dishDto.dishTypeId)
        return dishRepository.save(dishDto.toEntity(dishType))
    }

    fun updateDish(id: UUID, updatedDishDto: RequestDishDto): Dish {
        val dishType = dishTypeService.getDishTypeById(updatedDishDto.dishTypeId)
        val updatedDish = updatedDishDto.toEntity(dishType).apply { this.id = id }
        return dishRepository
            .findById(id)
            .map { dishRepository.save(updatedDish) }
            .orElseThrow { DishNotFoundException("Dish with id $id not found") }
    }

    fun deleteDish(id: UUID): Boolean {
        return if (dishRepository.existsById(id)) {
            dishRepository.deleteById(id)
            true // Deletion successful
        } else {
            false // Dish with the given ID not found
        }
    }
}