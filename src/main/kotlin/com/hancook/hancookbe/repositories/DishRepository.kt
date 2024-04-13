package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.Dish
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DishRepository : JpaRepository<Dish, UUID> {
    fun findAllByDeletedFalse(): List<Dish>
}