package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestOrderDetailDto
import com.hancook.hancookbe.dtos.ResponseOrderDetailDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.DishRepository
import com.hancook.hancookbe.repositories.OrderDetailRepository
import com.hancook.hancookbe.repositories.OrderRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class OrderDetailService(
    @Autowired private val orderDetailRepository: OrderDetailRepository,
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val dishRepository: DishRepository
) {
    fun findAllOrderDetailsByOrder(orderId: UUID): List<ResponseOrderDetailDto> {
        return orderDetailRepository
            .findAllById_CustomerOrder_Id(orderId = orderId)
            .map { it.toResponse() }
    }

    fun findOrderDetailByOrderAndDish(orderId: UUID, dishId: UUID): ResponseOrderDetailDto? {
        return orderDetailRepository
            .findById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)
            .map { it.toResponse() }
            .orElseThrow{ ElementNotFoundException(objectName = "Order detail", id = "Order ID: $orderId and Dish ID: $dishId") }
    }

    fun createOrderDetail(orderId: UUID, dishId: UUID, requestOrderDetailDto: RequestOrderDetailDto): ResponseOrderDetailDto {
        val order = orderRepository
            .findById(orderId)
            .orElseThrow { ElementNotFoundException(objectName = "Order", id = orderId.toString()) }

        val dish = dishRepository
            .findById(dishId)
            .orElseThrow { ElementNotFoundException(objectName = "Dish", id = dishId.toString()) }

        val detail = requestOrderDetailDto.toEntity(customerOrder = order, dish = dish)
        return orderDetailRepository.save(detail).toResponse()
    }

    fun updateOrderDetail(orderId: UUID, dishId: UUID, requestOrderDetailDto: RequestOrderDetailDto): ResponseOrderDetailDto {
        val detail = orderDetailRepository
            .findById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)
            .orElseThrow { ElementNotFoundException(objectName = "Order detail", id = "Order ID: $orderId and Dish ID: $dishId") }

        val newDetail = detail.apply {
            this.note = requestOrderDetailDto.note
            this.quantity = requestOrderDetailDto.quantity
        }

        return orderDetailRepository.save(newDetail).toResponse()
    }

    fun deleteOrderDetail(orderId: UUID, dishId: UUID) {
        if (!orderDetailRepository.existsById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)){
            throw ElementNotFoundException(objectName = "Order detail", id = "Order ID: $orderId and Dish ID: $dishId")
        }

        return orderDetailRepository.deleteById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)
    }
}