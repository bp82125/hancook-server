package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestCustomerOrderDto
import com.hancook.hancookbe.dtos.ResponseCustomerOrderDto
import com.hancook.hancookbe.services.CustomerOrderService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("\${api.endpoint.base-url}/tables")
class CustomerOrderController(
    @Autowired private val customerOrderService: CustomerOrderService
) {
    @GetMapping("/orders", "/orders/")
    fun findAllCustomerOrders(): ResponseEntity<ApiResponse<List<ResponseCustomerOrderDto>>> {
        val orders = customerOrderService.findAllCustomerOrders()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = orders,
                message = "Found orders"
            )
        )
    }

    @GetMapping("/{tableId}/orders", "/{tableId}/orders/")
    fun findCustomerOrderByTableId(
        @PathVariable tableId: UUID
    ): ResponseEntity<ApiResponse<ResponseCustomerOrderDto>> {
        val order = customerOrderService.findCustomerOrderByTableId(tableId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = order,
                message = "Found order of table with ID: $tableId"
            )
        )
    }

    @PostMapping("/{tableId}/orders", "/{tableId}/orders/")
    fun createCustomerOrder(
        @PathVariable tableId: UUID,
        @Valid @RequestBody requestCustomerOrderDto: RequestCustomerOrderDto
    ): ResponseEntity<ApiResponse<ResponseCustomerOrderDto>> {
        val createdOrder = customerOrderService.createCustomerOrder(tableId = tableId, requestCustomerOrderDto = requestCustomerOrderDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = createdOrder,
                    message = "Successfully created an order for table with ID: $tableId"
                )
            )
    }

    @DeleteMapping("/{tableId}/orders", "/{tableId}/orders/")
    fun deleteCustomerOrder(
        @PathVariable tableId: UUID,
    ): ResponseEntity<ApiResponse<Unit>> {
        customerOrderService.deleteCustomerOrder(tableId = tableId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                message = "Order has been deleted successfully"
            )
        )
    }
}