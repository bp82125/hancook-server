package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.OrderDetail
import com.hancook.hancookbe.models.compositeKeys.OrderDetailId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderDetailRepository : JpaRepository<OrderDetail, OrderDetailId>{
    fun findAllById_CustomerOrder_Id(orderId: UUID): List<OrderDetail>

    fun findById_CustomerOrder_IdAndId_Dish_Id(orderId: UUID, dishId: UUID): Optional<OrderDetail>

    fun existsById_CustomerOrder_IdAndId_Dish_Id(orderId: UUID, dishId: UUID): Boolean

    fun deleteById_CustomerOrder_IdAndId_Dish_Id(orderId: UUID, dishId: UUID): Unit
}