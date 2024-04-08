package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.ResponseInvoiceDetail
import com.hancook.hancookbe.services.InvoiceDetailService
import com.hancook.hancookbe.system.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("\${api.endpoint.base-url}")
class InvoiceDetailController(
    @Autowired private val invoiceDetailService: InvoiceDetailService
) {
    @GetMapping("/invoices/{invoiceId}/details")
    fun findDetailsOfInvoice(
        @PathVariable invoiceId: UUID
    ): ResponseEntity<ApiResponse<List<ResponseInvoiceDetail>>> {
        val details = invoiceDetailService.findAllDetailsOfInvoice(invoiceId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = details,
                message = "Found details of invoice with ID: $invoiceId"
            )
        )
    }

    @GetMapping("/invoices/{invoiceId}/details/{dishId}")
    fun findDetailsOfInvoice(
        @PathVariable invoiceId: UUID,
        @PathVariable dishId: UUID
    ): ResponseEntity<ApiResponse<ResponseInvoiceDetail>> {
        val detail = invoiceDetailService.findDetailOfInvoiceAndDish(invoiceId, dishId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = detail,
                message = "Found detail of invoice with ID: $invoiceId and dishID: $dishId"
            )
        )
    }
}