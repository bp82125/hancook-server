package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestOrderDetailDto
import com.hancook.hancookbe.dtos.ResponseOrderDetailDto
import com.hancook.hancookbe.services.OrderDetailService
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
class OrderDetailController(
    @Autowired private val orderDetailService: OrderDetailService
) {
    @GetMapping("/{orderId}/details", "/{orderId}/details/")
    fun findAllOrderDetailsByOrder(
        @PathVariable orderId: UUID
    ): ResponseEntity<ApiResponse<List<ResponseOrderDetailDto>>> {
        val details = orderDetailService.findAllOrderDetailsByOrder(orderId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = details,
                message = "Found details of order: $orderId"
            )
        )
    }

    @GetMapping("/{orderId}/details/{dishId}", "/{orderId}/details/{dishId}/")
    fun findOrderDetailByOrderAndDish(
        @PathVariable orderId: UUID,
        @PathVariable dishId: UUID,
    ): ResponseEntity<ApiResponse<ResponseOrderDetailDto>> {
        val detail = orderDetailService.findOrderDetailByOrderAndDish(orderId, dishId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = detail,
                message = "Found details of order: $orderId and dish: $dishId"
            )
        )
    }

    @PostMapping("/{orderId}/details/{dishId}", "/{orderId}/details/{dishId}/")
    fun createOrderDetail(
        @PathVariable orderId: UUID,
        @PathVariable dishId: UUID,
        @Valid @RequestBody requestOrderDetailDto: RequestOrderDetailDto,
    ): ResponseEntity<ApiResponse<ResponseOrderDetailDto>> {
        val createdDetail = orderDetailService.createOrderDetail(orderId, dishId, requestOrderDetailDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = createdDetail,
                    message = "Successfully created an order detail of order: $orderId and dish: $dishId"
                )
            )
    }

    @PutMapping("/{orderId}/details/{dishId}", "/{orderId}/details/{dishId}/")
    fun updateOrderDetail(
        @PathVariable orderId: UUID,
        @PathVariable dishId: UUID,
        @Valid @RequestBody requestOrderDetailDto: RequestOrderDetailDto,
    ): ResponseEntity<ApiResponse<ResponseOrderDetailDto>> {
        val updatedDetail = orderDetailService.updateOrderDetail(orderId, dishId, requestOrderDetailDto)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedDetail,
                message = "Updated success order detail of order: $orderId and dish: $dishId"
            )
        )
    }

    @DeleteMapping("/{orderId}/details/{dishId}", "/{orderId}/details/{dishId}/")
    fun deleteOrderDetail(
        @PathVariable orderId: UUID,
        @PathVariable dishId: UUID,
    ): ResponseEntity<ApiResponse<Unit>> {
        orderDetailService.deleteOrderDetail(orderId, dishId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                message = "Order detail has been deleted successfully"
            )
        )
    }
}