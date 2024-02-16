package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestDishDto
import com.hancook.hancookbe.dtos.ResponseDishDto
import com.hancook.hancookbe.services.DishService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/dishes")
class DishController(
    @Autowired private val dishService: DishService,
) {

    @GetMapping("", "/")
    fun getAllDishes(): ResponseEntity<ApiResponse<List<ResponseDishDto>>> {
        val dishes = dishService.getAllDishes()
        return ResponseEntity.ok(ApiResponse(success = true, data = dishes, message = "Found dishes"))
    }

    @GetMapping("/{id}")
    fun getDishById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponseDishDto>> {
        val dish = dishService.getDishById(id)
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
        @Valid @RequestBody requestDish: RequestDishDto
    ): ResponseEntity<ApiResponse<ResponseDishDto>> {
        val responseDish = dishService.createDish(requestDish = requestDish)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    ApiResponse(
                        success = true,
                        data = responseDish,
                        message = "Successfully created a dish"
                    )
                )
    }

    @PutMapping("/{id}")
    fun updateDish(
        @PathVariable id: UUID,
        @Valid @RequestBody requestDish: RequestDishDto
    ): ResponseEntity<ApiResponse<ResponseDishDto>> {
        val responseDish = dishService.updateDish(id, requestDish)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                data = responseDish,
                message = "Updated success"
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteDish(@PathVariable id: UUID): ResponseEntity<ApiResponse<Unit>> {
        dishService.deleteDish(id)
        return ResponseEntity.ok(ApiResponse(success = true, message = "Dish has been deleted successfully"))
    }
}