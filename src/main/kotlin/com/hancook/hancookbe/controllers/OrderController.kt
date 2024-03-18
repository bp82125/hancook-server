package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestOrderDto
import com.hancook.hancookbe.dtos.ResponseOrderDto
import com.hancook.hancookbe.services.OrderService
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
@RequestMapping("\${api.endpoint.base-url}/orders")
class OrderController(
    @Autowired private val orderService: OrderService
) {
    @GetMapping("", "/")
    fun findAllOrders(): ResponseEntity<ApiResponse<List<ResponseOrderDto>>> {
        val orders = orderService.findAllOrders()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = orders,
                message = "Found orders"
            )
        )
    }

    @GetMapping("/{id}", "/{id}/")
    fun findOrderById(
        @PathVariable id: UUID
    ): ResponseEntity<ApiResponse<ResponseOrderDto>> {
        val order = orderService.findOrderById(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = order,
                message = "Found order with ID: $id"
            )
        )
    }

    @PostMapping("", "/")
    fun createOrder(
        @Valid @RequestBody requestOrderDto: RequestOrderDto
    ): ResponseEntity<ApiResponse<ResponseOrderDto>> {
        val createdOrder = orderService.createOrder(requestOrderDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = createdOrder,
                    message = "Successfully created an order"
                )
            )
    }

    @PutMapping("/{id}", "/{id}/")
    fun updateOrder(
        @PathVariable id : UUID,
        @Valid @RequestBody requestOrderDto: RequestOrderDto
    ): ResponseEntity<ApiResponse<ResponseOrderDto>> {
        val updatedOrder = orderService.updateOrder(orderId = id, requestOrderDto = requestOrderDto)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedOrder,
                message = "Updated success"
            )
        )
    }

    @DeleteMapping("/{id}", "/{id}/")
    fun deleteOrder(
        @PathVariable id : UUID,
    ): ResponseEntity<ApiResponse<Unit>> {
        orderService.deleteOrder(orderId = id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                message = "Account has been deleted successfully"
            )
        )
    }
}