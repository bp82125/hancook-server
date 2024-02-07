package com.hancook.hancookbe.controller

import com.hancook.hancookbe.converter.toEntity
import com.hancook.hancookbe.converter.toResponse
import com.hancook.hancookbe.dto.RequestDishDto
import com.hancook.hancookbe.dto.ResponseDishDto
import com.hancook.hancookbe.service.DishService
import com.hancook.hancookbe.service.DishTypeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/api/v1/dishes")
class DishController(
    @Autowired private val dishService: DishService,
    @Autowired private val dishTypeService: DishTypeService
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
        @Valid @RequestBody dishDto: RequestDishDto
    ): ResponseEntity<ApiResponse<ResponseDishDto>> {
            val dishType = dishTypeService.getDishTypeById(dishDto.dishTypeId)
            val dish = dishDto.toEntity(dishType)
            val createdDish = dishService.createDish(dish)
            val responseDishDto = createdDish.toResponse()

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse(
                        success = true,
                        data = responseDishDto,
                        message = "Successfully created a dish")
                    )
        }

    @PutMapping("/{id}")
    fun updateDish(
        @PathVariable id: UUID,
        @Valid @RequestBody dishDto: RequestDishDto
    ): ResponseEntity<ApiResponse<ResponseDishDto>> {
        val dishType = dishTypeService.getDishTypeById(dishDto.dishTypeId)
        val dish = dishDto.toEntity(dishType)
        val updatedDish = dishService.updateDish(id, dish)
        val responseDishDto = updatedDish.toResponse()

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                data = responseDishDto,
                message = "Updated success"
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteDish(@PathVariable id: UUID): ResponseEntity<ApiResponse<Unit>> {
        dishService.deleteDish(id)
        return ResponseEntity.ok(ApiResponse(success = true, message = "Dish deleted successfully"))
    }
}