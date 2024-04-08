package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.Invoice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface InvoiceRepository : JpaRepository<Invoice, UUID> {
    fun findAllByCreatedTimeBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Invoice>
}