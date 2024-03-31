package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.CustomerOrderDetail
import com.hancook.hancookbe.models.compositeKeys.CustomerOrderDetailId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerOrderDetailRepository : JpaRepository<CustomerOrderDetail, CustomerOrderDetailId>{
    fun findAllById_CustomerOrder_Id(orderId: UUID): List<CustomerOrderDetail>

    fun findById_CustomerOrder_IdAndId_Dish_Id(orderId: UUID, dishId: UUID): Optional<CustomerOrderDetail>

    fun existsById_CustomerOrder_IdAndId_Dish_Id(orderId: UUID, dishId: UUID): Boolean

    fun deleteById_CustomerOrder_IdAndId_Dish_Id(orderId: UUID, dishId: UUID): Unit
}