package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestInvoice
import com.hancook.hancookbe.dtos.ResponseInvoice
import com.hancook.hancookbe.services.InvoiceService
import com.hancook.hancookbe.system.ApiResponse
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
@RequestMapping("\${api.endpoint.base-url}")
class InvoiceController(
    @Autowired private val invoiceService: InvoiceService
) {
    @GetMapping("/invoices", "/invoices/")
    fun findAllInvoices(): ResponseEntity<ApiResponse<List<ResponseInvoice>>> {
        val invoices = invoiceService.findAllInvoices()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = invoices,
                message = "Found invoices"
            )
        )
    }

    @GetMapping("/invoices/{invoiceId}", "/invoices/{invoiceId}/")
    fun findInvoiceById(
        @PathVariable invoiceId: UUID
    ): ResponseEntity<ApiResponse<ResponseInvoice>> {
        val invoice = invoiceService.findInvoiceById(invoiceId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = invoice,
                message = "Found invoice with ID: $invoiceId"
            )
        )
    }

    @PostMapping("/orders/{orderId}/invoices", "/orders/{orderId}/invoices/")
    fun createInvoice(
        @PathVariable orderId: UUID,
        @RequestBody requestInvoice: RequestInvoice
    ): ResponseEntity<ApiResponse<ResponseInvoice>> {
        val createdInvoice = invoiceService.createInvoice(orderId, requestInvoice)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = createdInvoice,
                message = "Created invoice of order ID: $orderId"
            )
        )
    }

    @DeleteMapping("/invoices/{invoiceId}", "/invoices/{invoiceId}/")
    fun deleteInvoice(
        @PathVariable invoiceId: UUID,
    ): ResponseEntity<ApiResponse<ResponseInvoice>> {
        invoiceService.deleteInvoice(invoiceId)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = null,
                message = "Successfully deleted invoice: $invoiceId"
            )
        )
    }
}