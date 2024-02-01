package com.hancook.hancookbe.controller

import com.hancook.hancookbe.model.DishType
import com.hancook.hancookbe.service.DishTypeService
import com.hancook.hancookbe.service.DishTypeServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dishType")
class DishTypeController(private val dishTypeServiceImpl: DishTypeServiceImpl) {

    @GetMapping
    fun getAllDishTypes(): List<DishType> {
        return dishTypeServiceImpl.getAllDishTypes()
    }

    @GetMapping("/{id}")
    fun getDishTypeById(@PathVariable id: Long): ResponseEntity<DishType> {
        val dishType = dishTypeServiceImpl.getDishTypeById(id)
        return if (dishType != null) {
            ResponseEntity.ok(dishType)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createDishType(@RequestBody dishType: DishType): ResponseEntity<DishType> {
        val createdDishType = dishTypeServiceImpl.createDishType(dishType)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDishType)
    }

    @PutMapping("/{id}")
    fun updateDishType(@PathVariable id: Long, @RequestBody updatedDishType: DishType): ResponseEntity<DishType> {
        val updatedEntity = dishTypeServiceImpl.updateDishType(id, updatedDishType)
        return if (updatedEntity != null) {
            ResponseEntity.ok(updatedEntity)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDishType(@PathVariable id: Long): ResponseEntity<Unit> {
        val isDeleted = dishTypeServiceImpl.deleteDishType(id)

        return if (isDeleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}