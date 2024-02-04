package com.hancook.hancookbe.controller

import com.hancook.hancookbe.model.DishType
import com.hancook.hancookbe.service.DishTypeService
import com.hancook.hancookbe.service.DishTypeServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/api/v1/dishType")
class DishTypeController(private val dishTypeServiceImpl: DishTypeServiceImpl) {

    @GetMapping
    fun getAllDishTypes(): ResponseEntity<Map<String, Any>> {
        val dishTypes = dishTypeServiceImpl.getAllDishTypes()
        return ResponseEntity.ok(mapOf("data" to dishTypes))
    }

    @GetMapping("/{id}")
    fun getDishTypeById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val dishType = dishTypeServiceImpl.getDishTypeById(id)
        return if (dishType != null) {
            ResponseEntity.ok(mapOf("data" to dishType))
        } else {
            val errorMessage = "DishType with ID $id not found"
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to errorMessage))
        }
    }

    @PostMapping
    fun createDishType(@RequestBody dishType: DishType): ResponseEntity<Map<String, Any>> {
        val createdDishType = dishTypeServiceImpl.createDishType(dishType)
        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("data" to createdDishType))
    }

    @PutMapping("/{id}")
    fun updateDishType(@PathVariable id: Long, @RequestBody updatedDishType: DishType): ResponseEntity<Map<String, Any>> {
        val updatedEntity = dishTypeServiceImpl.updateDishType(id, updatedDishType)
        return if (updatedEntity != null) {
            ResponseEntity.ok(mapOf("data" to updatedEntity))
        } else {
            val errorMessage = "DishType with ID $id not found"
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to errorMessage))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDishType(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val isDeleted = dishTypeServiceImpl.deleteDishType(id)

        return if (isDeleted) {
            ResponseEntity.noContent().build()
        } else {
            val errorMessage = "DishType with ID $id not found"
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to errorMessage))
        }
    }
}