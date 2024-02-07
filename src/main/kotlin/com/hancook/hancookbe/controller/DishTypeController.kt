package com.hancook.hancookbe.controller

import com.hancook.hancookbe.converter.toEntity
import com.hancook.hancookbe.converter.toResponse
import com.hancook.hancookbe.dto.RequestDishTypeDto
import com.hancook.hancookbe.dto.ResponseDishTypeDto
import com.hancook.hancookbe.service.DishTypeService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/dishTypes")
class DishTypeController(private val dishTypeService: DishTypeService) {

    @GetMapping("", "/")
    fun getAllDishTypes(): ResponseEntity<ApiResponse<List<ResponseDishTypeDto>>> {
        val dishTypes = dishTypeService.getAllDishTypes().map { it.toResponse() }
        return ResponseEntity.ok(ApiResponse(success = true, data = dishTypes))
    }

    @GetMapping("/{id}")
    fun getDishTypeById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponseDishTypeDto>> {
        val responseDishType = dishTypeService.getDishTypeById(id).toResponse()
        return ResponseEntity.ok(ApiResponse(success = true, data = responseDishType))
    }

    @PostMapping
    fun createDishType(
        @Valid @RequestBody dishTypeDto: RequestDishTypeDto
    ): ResponseEntity<ApiResponse<ResponseDishTypeDto>> {
        val entity = dishTypeDto.toEntity()
        val createdDishType = dishTypeService.createDishType(dishTypeDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse(success = true, data = createdDishType.toResponse(), message = "Successfully created a dish type"))
    }

    @PutMapping("/{id}")
    fun updateDishType(
        @PathVariable id: UUID,
        @Valid @RequestBody dishTypeDto: RequestDishTypeDto
    ): ResponseEntity<ApiResponse<ResponseDishTypeDto>> {
        val updatedDishType = dishTypeService.updateDishType(id, dishTypeDto).toResponse()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                data = updatedDishType,
                message = "Updated success"
            )
        )

    }
//
//    @DeleteMapping("/{id}")
//    fun deleteDishType(@PathVariable id: UUID): ResponseEntity<ApiResponse<Unit>> {
//        val isDeleted = dishTypeService.deleteDishType(id)
//
//        return if (isDeleted) {
//            ResponseEntity.noContent().build()
//        } else {
//            val errorMessage = "DishType with ID $id not found"
//            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(success = false, error = errorMessage))
//        }
//    }
}