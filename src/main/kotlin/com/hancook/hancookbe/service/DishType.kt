package com.hancook.hancookbe.service

import com.hancook.hancookbe.model.DishType

interface DishTypeService {
    fun getAllDishTypes(): List<DishType>
    fun getDishTypeById(id: Long): DishType?
    fun createDishType(dishType: DishType): DishType
    fun updateDishType(id: Long, updatedDishType: DishType): DishType?
    fun deleteDishType(id: Long): Boolean
}