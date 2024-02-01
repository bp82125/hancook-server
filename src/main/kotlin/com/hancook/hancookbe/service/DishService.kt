package com.hancook.hancookbe.service

import com.hancook.hancookbe.model.Dish

interface DishService {
    fun getAllDishes(): List<Dish>
    fun getDishById(id: Long): Dish?
    fun createDish(dish: Dish): Dish
    fun updateDish(id: Long, updatedDish: Dish): Dish?
    fun deleteDish(id: Long): Boolean
}