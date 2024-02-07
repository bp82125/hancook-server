package com.hancook.hancookbe.controller

import com.hancook.hancookbe.converter.toResponse
import com.hancook.hancookbe.dto.RequestDishDto
import com.hancook.hancookbe.dto.ResponseDishDto
import com.hancook.hancookbe.service.DishService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/v1/dishes")
class DishController(
    private val dishService: DishService
) {

    @GetMapping("", "/")
    fun getAllDishes(): ResponseEntity<ApiResponse<List<ResponseDishDto>>> {
        val dishes = dishService.getAllDishes().map { it.toResponse() }
        return ResponseEntity.ok(ApiResponse(success = true, data = dishes, message = "Found dishes"))
    }

    @GetMapping("/{id}")
    fun getDishById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponseDishDto>> {
        val dish = dishService.getDishById(id).toResponse()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                data = dish,
                message = "Found dish with ID: $id"
            )
        )
    }

    @PostMapping
    fun createDish(
        @Valid
        @RequestBody
        dishDto: RequestDishDto
    )
    : ResponseEntity<ApiResponse<ResponseDishDto>> {
            val createdDish = dishService.createDish(dishDto)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse(success = true, data = createdDish.toResponse(), message = "Successfully created a dish"))

        }

    @PutMapping("/{id}")
    fun updateDish(
        @PathVariable
        id: UUID,

        @Valid
        @RequestBody dishDto: RequestDishDto
    ): ResponseEntity<ApiResponse<ResponseDishDto>> {
        val updatedDish = dishService.updateDish(id, dishDto).toResponse()
        return ResponseEntity.ok(ApiResponse(success = true, data = updatedDish, message = "Updated success"))
    }
//
//    @DeleteMapping("/{id}")
//    fun deleteDish(@PathVariable id: UUID): ResponseEntity<ApiResponse<Unit>> {
//        val isDeleted = dishService.deleteDish(id)
//
//        return if (isDeleted) {
//            ResponseEntity.noContent().build()
//        } else {
//            val errorMessage = "Dish with ID $id not found"
//            ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ApiResponse(success = false, error = errorMessage))
//        }
//    }
}