package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.ResponseInvoiceDetail
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.InvoiceDetailRepository
import com.hancook.hancookbe.repositories.InvoiceRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class InvoiceDetailService(
    @Autowired private val dishRepository: InvoiceRepository,
    @Autowired private val invoiceRepository: InvoiceRepository,
    @Autowired private val invoiceDetailRepository: InvoiceDetailRepository
) {
    fun findAllDetailsOfInvoice(invoiceId: UUID): List<ResponseInvoiceDetail> {
        if (!invoiceRepository.existsById(invoiceId)){
            throw ElementNotFoundException(objectName = "Invoice", id = invoiceId.toString())
        }

        val details = invoiceDetailRepository.findAllById_Invoice_Id(invoiceId)
        return details.map { it.toResponse() }
    }

    fun findDetailOfInvoiceAndDish(invoiceId: UUID, dishId: UUID): ResponseInvoiceDetail {
        if (invoiceRepository.existsById(invoiceId)){
            throw ElementNotFoundException(objectName = "Invoice", id = invoiceId.toString())
        }

        if (!dishRepository.existsById(dishId)){
            throw ElementNotFoundException(objectName = "Dish", id = dishId.toString())
        }

        val detail = invoiceDetailRepository
            .findById_Invoice_IdAndId_Dish_Id(invoiceId, dishId)
            .orElseThrow { ElementNotFoundException(objectName = "Invoice detail", id = "Invoice ID: $invoiceId, Dish ID: $dishId") }

        return detail.toResponse()
    }
}