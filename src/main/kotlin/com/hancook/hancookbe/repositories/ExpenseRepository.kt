package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.Expense
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface ExpenseRepository : JpaRepository<Expense, UUID>