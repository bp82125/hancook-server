package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.InvoiceDetail
import com.hancook.hancookbe.models.compositeKeys.InvoiceDetailId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@Repository
interface InvoiceDetailRepository : JpaRepository<InvoiceDetail, InvoiceDetailId> {
    fun findAllById_Invoice_Id(invoiceId: UUID) : List<InvoiceDetail>

    fun findById_Invoice_IdAndId_Dish_Id(invoiceId: UUID, dishId: UUID): Optional<InvoiceDetail>

    fun existsById_Invoice_IdAndId_Dish_Id(invoiceId: UUID, dishId: UUID): Boolean

    fun deleteById_Invoice_IdAndId_Dish_Id(invoiceId: UUID, dishId: UUID)

    @Query("SELECT d.id.dish, COUNT(d) FROM InvoiceDetail d GROUP BY d.id.dish ORDER BY COUNT(d) DESC")
    fun countTop5DishesByCheckoutDetails(): List<Array<Any>>

    @Query("SELECT d.id.dish, COUNT(d) FROM InvoiceDetail d WHERE d.id.invoice.createdTime BETWEEN :startTime AND :endTime GROUP BY d.id.dish ORDER BY COUNT(d) DESC LIMIT 5")
    fun countTop5DishesByCheckoutDetails(startTime: LocalDateTime, endTime: LocalDateTime): List<Array<Any>>
}