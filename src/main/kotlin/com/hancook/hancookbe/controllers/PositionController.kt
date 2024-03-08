package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestPositionDto
import com.hancook.hancookbe.dtos.ResponsePositionDto
import com.hancook.hancookbe.services.PositionService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("\${api.endpoint.base-url}/positions")
class PositionController(@Autowired private val positionService: PositionService) {

    @GetMapping("", "/")
    fun getAllPositions(): ResponseEntity<ApiResponse<List<ResponsePositionDto>>> {
        val responsePositions = positionService.getAllPosition()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = responsePositions,
                message = "Found positions"
            )
        )
    }

    @GetMapping("/{id}")
    fun getPositionById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponsePositionDto>> {
        val responsePosition = positionService.getPositionById(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = responsePosition,
                message = "Found a position"
            )
        )
    }

    @PostMapping
    fun createPosition(
        @Valid @RequestBody requestPosition: RequestPositionDto
    ): ResponseEntity<ApiResponse<ResponsePositionDto>> {
        val createdPosition = positionService.createPosition(requestPosition)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = createdPosition,
                    message = "Successfully created a position"
                )
            )
    }

    @PutMapping("/{id}")
    fun updatePosition(
        @PathVariable id: UUID,
        @Valid @RequestBody requestPosition: RequestPositionDto
    ): ResponseEntity<ApiResponse<ResponsePositionDto>> {
        val updatedPosition = positionService.updatePosition(id = id, requestPosition = requestPosition)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedPosition,
                message = "Updated success"
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deletePosition(
        @PathVariable id: UUID
    ): ResponseEntity<ApiResponse<Unit>> {
        positionService.deletePosition(id)
        return ResponseEntity.ok(ApiResponse(success = true, statusCode = HttpStatus.OK.value(), message = "Position has been deleted successfully"))
    }
}