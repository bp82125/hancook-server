package com.hancook.hancookbe.controller

import com.hancook.hancookbe.model.Dish
import com.hancook.hancookbe.service.DishServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/dish")
class DishController(private val dishServiceImpl: DishServiceImpl) {

    @GetMapping
    fun getAllDishes(): ResponseEntity<Map<String, Any>> {
        val dishes = dishServiceImpl.getAllDishes()
        return ResponseEntity.ok(mapOf("data" to dishes))
    }

    @GetMapping("/{id}")
    fun getDishById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val dish = dishServiceImpl.getDishById(id)
        return if (dish != null) {
            ResponseEntity.ok(mapOf("data" to dish))
        } else {
            val errorMessage = "Dish with ID $id not found"
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to errorMessage))
        }
    }

    @PostMapping
    fun createDish(@RequestBody dish: Dish): ResponseEntity<Map<String, Any>> {
        val createdDish = dishServiceImpl.createDish(dish)
        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("data" to createdDish))
    }

    @PutMapping("/{id}")
    fun updateDish(@PathVariable id: Long, @RequestBody updatedDish: Dish): ResponseEntity<Map<String, Any>> {
        val updatedEntity = dishServiceImpl.updateDish(id, updatedDish)
        return if (updatedEntity != null) {
            ResponseEntity.ok(mapOf("data" to updatedEntity))
        } else {
            val errorMessage = "Dish with ID $id not found"
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to errorMessage))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDish(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val deleted = dishServiceImpl.deleteDish(id)

        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            val errorMessage = "Dish with ID $id not found"
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to errorMessage))
        }
    }
}