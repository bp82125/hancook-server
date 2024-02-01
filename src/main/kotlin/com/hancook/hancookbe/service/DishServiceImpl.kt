package com.hancook.hancookbe.service

import com.hancook.hancookbe.model.Dish
import com.hancook.hancookbe.repository.DishRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable


@Service
class DishServiceImpl(private val dishRepository: DishRepository) : DishService {

    override fun getAllDishes(): List<Dish> {
        return dishRepository.findAll()
    }

    override fun getDishById(id: Long): Dish? {
        return dishRepository.findById(id).orElse(null)
    }

    override fun createDish(dish: Dish): Dish {
        return dishRepository.save(dish)
    }

    override fun updateDish(id: Long, updatedDish: Dish): Dish? {
        return dishRepository.findById(id).map { existingDish ->
            existingDish.apply {
                dishName = updatedDish.dishName
                price = updatedDish.price
                imagePath = updatedDish.imagePath
                dishType = updatedDish.dishType
            }
        }.orElse(null)
    }

    override fun deleteDish(id: Long): Boolean {
        return try {
            dishRepository.deleteById(id)
            true // Deletion successful
        } catch (e: EmptyResultDataAccessException) {
            false // DishType with the given ID not found
        }
    }
}