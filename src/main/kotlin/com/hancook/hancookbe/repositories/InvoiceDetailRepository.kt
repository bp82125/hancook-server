package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.InvoiceDetail
import com.hancook.hancookbe.models.compositeKeys.InvoiceDetailId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface InvoiceDetailRepository : JpaRepository<InvoiceDetail, InvoiceDetailId> {
    fun findAllById_Invoice_Id(invoiceId: UUID) : List<InvoiceDetail>

    fun findById_Invoice_IdAndId_Dish_Id(invoiceId: UUID, dishId: UUID): Optional<InvoiceDetail>

    fun existsById_Invoice_IdAndId_Dish_Id(invoiceId: UUID, dishId: UUID): Boolean

    fun deleteById_Invoice_IdAndId_Dish_Id(invoiceId: UUID, dishId: UUID)
}