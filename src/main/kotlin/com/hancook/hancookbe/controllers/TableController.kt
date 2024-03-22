package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestTableDto
import com.hancook.hancookbe.dtos.ResponseOrderDto
import com.hancook.hancookbe.dtos.ResponseTableDto
import com.hancook.hancookbe.services.OrderService
import com.hancook.hancookbe.services.TableService
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
@RequestMapping("\${api.endpoint.base-url}/tables")
class TableController(
    @Autowired private val tableService: TableService,
    @Autowired private val orderService: OrderService
) {
    @GetMapping("", "/")
    fun findAllTables(): ResponseEntity<ApiResponse<List<ResponseTableDto>>> {
        val tables =  tableService.findAllTables()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = tables,
                message = "Found tables"
            )
        )
    }

    @GetMapping("/{id}", "/{id}/")
    fun findTableById(
        @PathVariable id: UUID
    ): ResponseEntity<ApiResponse<ResponseTableDto>> {
        val table = tableService.findTableById(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = table,
                message = "Found table with ID: $id"
            )
        )
    }

    @GetMapping("/{tableId}/orders", "/{tableId}/orders")
    fun findOrderByTableId(
        @PathVariable tableId: UUID
    ): ResponseEntity<ApiResponse<ResponseOrderDto>> {
        val order = orderService.findOrderByTableId(tableId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = order,
                message = "Found order of table with ID: $tableId"
            )
        )
    }

    @PostMapping("", "/")
    fun createTable(
        @Valid @RequestBody requestTableDto: RequestTableDto
    ): ResponseEntity<ApiResponse<ResponseTableDto>> {
        val createdTable = tableService.createTable(requestTableDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = createdTable,
                    message = "Successfully created a table"
                )
            )
    }

    @PutMapping("/{id}", "/{id}/")
    fun updateTable(
        @PathVariable id: UUID,
        @Valid @RequestBody requestTableDto: RequestTableDto
    ): ResponseEntity<ApiResponse<ResponseTableDto>> {
        val updatedTable = tableService.updateTable(id, requestTableDto)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = updatedTable,
                message = "Updated success"
            )
        )
    }

    @DeleteMapping("/{id}", "/{id}/")
    fun deleteTable(
        @PathVariable id: UUID
    ): ResponseEntity<ApiResponse<Unit>> {
        tableService.deleteTable(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                message = "Table has been deleted successfully"
            )
        )
    }
}