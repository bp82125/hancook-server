package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toInvoice
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestInvoice
import com.hancook.hancookbe.dtos.ResponseInvoice
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.CustomerOrderRepository
import com.hancook.hancookbe.repositories.EmployeeRepository
import com.hancook.hancookbe.repositories.InvoiceRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class InvoiceService(
    @Autowired private val invoiceRepository: InvoiceRepository,
    @Autowired private val customerOrderRepository: CustomerOrderRepository,
    @Autowired private val employeeRepository: EmployeeRepository
) {
    fun findAllInvoices(): List<ResponseInvoice> {
        return invoiceRepository
            .findAll()
            .map { it.toResponse() }
    }

    fun findInvoiceById(invoiceId: UUID): ResponseInvoice {
        return invoiceRepository
            .findById(invoiceId)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Invoice", id = invoiceId.toString()) }
    }

    fun createInvoice(orderId: UUID, requestInvoice: RequestInvoice): ResponseInvoice {
        val order = customerOrderRepository.findById(orderId).orElseThrow {
            ElementNotFoundException(objectName = "Order", id = orderId.toString())
        }

        val employeeId = requestInvoice.employeeId

        val employee = employeeRepository.findById(employeeId).orElseThrow {
            ElementNotFoundException(objectName = "Employee", id = employeeId.toString())
        }

        val invoice = order.toInvoice(employee, requestInvoice.customerPayment)
        val createdInvoice = invoiceRepository.save(invoice)

        customerOrderRepository.deleteById(orderId)

        return createdInvoice.toResponse()
    }

    fun deleteInvoice(invoiceId: UUID) {
        if(!invoiceRepository.existsById(invoiceId)){
            throw ElementNotFoundException(objectName = "Invoice", id = invoiceId.toString())
        }
        invoiceRepository.deleteById(invoiceId)
    }
}