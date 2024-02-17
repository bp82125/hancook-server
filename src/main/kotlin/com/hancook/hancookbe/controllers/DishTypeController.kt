package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestDishTypeDto
import com.hancook.hancookbe.dtos.ResponseDishTypeDto
import com.hancook.hancookbe.services.DishTypeService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("\${api.endpoint.base-url}/dishTypes")
class DishTypeController(
    @Autowired private val dishTypeService: DishTypeService
) {

    @GetMapping("", "/")
    fun getAllDishTypes(): ResponseEntity<ApiResponse<List<ResponseDishTypeDto>>> {
        val dishTypes = dishTypeService.getAllDishTypes()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                data = dishTypes,
                message = "Found dish types"
            )
        )
    }

    @GetMapping("/{id}")
    fun getDishTypeById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponseDishTypeDto>> {
        val responseDishType = dishTypeService.getDishTypeById(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                data = responseDishType,
                message = "Found a dish type"
            )
        )
    }

    @PostMapping
    fun createDishType(
        @Valid @RequestBody requestDishType: RequestDishTypeDto
    ): ResponseEntity<ApiResponse<ResponseDishTypeDto>> {
        val responseDishType = dishTypeService.createDishType(requestDishType)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    data = responseDishType,
                    message = "Successfully created a dish type"
                )
            )
    }

    @PutMapping("/{id}")
    fun updateDishType(
        @PathVariable id: UUID,
        @Valid @RequestBody requestDishType: RequestDishTypeDto
    ): ResponseEntity<ApiResponse<ResponseDishTypeDto>> {
        val responseDishType = dishTypeService.updateDishType(id = id, requestDishType = requestDishType)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                data = responseDishType,
                message = "Updated success"
            )
        )

    }

    @DeleteMapping("/{id}")
    fun deleteDishType(@PathVariable id: UUID): ResponseEntity<ApiResponse<Unit>> {
        dishTypeService.deleteDishType(id)
        return ResponseEntity.ok(ApiResponse(success = true, message = "Dish type has been deleted successfully"))
    }
}