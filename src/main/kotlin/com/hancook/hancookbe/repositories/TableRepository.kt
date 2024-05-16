package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.enums.TableState
import com.hancook.hancookbe.models.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TableRepository : JpaRepository<Table, UUID>{
    fun countByCustomerOrderIsNotNull(): Long
    fun countByCustomerOrderIsNull(): Long

    fun findAllByDeletedFalse(): List<Table>
}