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

    fun createDish(dish: Dish): Dish {
        return dishRepository.save(dish)
    }

    fun updateDish(id: UUID, dish: Dish): Dish {
        val updatedDish = dish.apply { this.id = id }
        return dishRepository
            .findById(id)
            .map { dishRepository.save(updatedDish) }
            .orElseThrow { DishNotFoundException("Dish with id $id not found") }
    }

    fun deleteDish(id: UUID) {
        dishRepository
            .findById(id)
            .map { dishRepository.deleteById(id) }
            .orElseThrow { DishNotFoundException("Dish with id $id not found") }
    }
}