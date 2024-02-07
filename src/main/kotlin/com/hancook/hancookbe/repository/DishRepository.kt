package com.hancook.hancookbe.repository

import com.hancook.hancookbe.model.Dish
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DishRepository : JpaRepository<Dish, UUID>