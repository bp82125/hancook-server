package com.hancook.hancookbe.controller

import com.hancook.hancookbe.model.Dish
import com.hancook.hancookbe.service.DishServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dish")
class DishController(private val dishServiceImpl: DishServiceImpl) {

    @GetMapping
    fun getAllDishes(): List<Dish> {
        return dishServiceImpl.getAllDishes()
    }

    @GetMapping("/{id}")
    fun getDishById(@PathVariable id: Long): ResponseEntity<Dish> {
        val dish = dishServiceImpl.getDishById(id)
        return if (dish != null) {
            ResponseEntity.ok(dish)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createDish(@RequestBody dish: Dish): ResponseEntity<Dish> {
        val createdDish = dishServiceImpl.createDish(dish)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDish)
    }

    @PutMapping("/{id}")
    fun updateDish(@PathVariable id: Long, @RequestBody updatedDish: Dish): ResponseEntity<Dish> {
        val updatedEntity = dishServiceImpl.updateDish(id, updatedDish)
        return if (updatedEntity != null) {
            ResponseEntity.ok(updatedEntity)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDish(@PathVariable id: Long): ResponseEntity<Unit> {
        dishServiceImpl.deleteDish(id)
        return ResponseEntity.noContent().build()
    }
}