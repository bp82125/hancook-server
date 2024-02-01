package com.hancook.hancookbe.repository

import com.hancook.hancookbe.model.DishType
import org.springframework.data.jpa.repository.JpaRepository

interface DishTypeRepository : JpaRepository<DishType, Long>