package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestOrderDetailDto
import com.hancook.hancookbe.dtos.ResponseOrderDetailDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.DishRepository
import com.hancook.hancookbe.repositories.CustomerOrderDetailRepository
import com.hancook.hancookbe.repositories.CustomerOrderRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class CustomerOrderDetailService(
    @Autowired private val customerOrderDetailRepository: CustomerOrderDetailRepository,
    @Autowired private val customerOrderRepository: CustomerOrderRepository,
    @Autowired private val dishRepository: DishRepository
) {
    fun findAllDetailsByOrder(orderId: UUID): List<ResponseOrderDetailDto> {
        if (!customerOrderRepository.existsById(orderId)){
            throw ElementNotFoundException(objectName = "Customer Order", id = orderId.toString())
        }

        return customerOrderDetailRepository
            .findAllById_CustomerOrder_Id(orderId = orderId)
            .map { it.toResponse() }
    }

    fun findDetailByOrderAndDish(orderId: UUID, dishId: UUID): ResponseOrderDetailDto? {
        return customerOrderDetailRepository
            .findById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)
            .map { it.toResponse() }
            .orElseThrow{ ElementNotFoundException(objectName = "Order detail", id = "Order ID: $orderId and Dish ID: $dishId") }
    }

    fun createOrderDetail(orderId: UUID, dishId: UUID, requestOrderDetailDto: RequestOrderDetailDto): ResponseOrderDetailDto {
        val order = customerOrderRepository
            .findById(orderId)
            .orElseThrow { ElementNotFoundException(objectName = "Order", id = orderId.toString()) }

        val dish = dishRepository
            .findById(dishId)
            .orElseThrow { ElementNotFoundException(objectName = "Dish", id = dishId.toString()) }

        val detail = requestOrderDetailDto.toEntity(customerOrder = order, dish = dish)
        return customerOrderDetailRepository.save(detail).toResponse()
    }

    fun updateOrderDetail(orderId: UUID, dishId: UUID, requestOrderDetailDto: RequestOrderDetailDto): ResponseOrderDetailDto {
        val detail = customerOrderDetailRepository
            .findById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)
            .orElseThrow { ElementNotFoundException(objectName = "Order detail", id = "Order ID: $orderId and Dish ID: $dishId") }

        val newDetail = detail.apply {
            this.note = requestOrderDetailDto.note
            this.quantity = requestOrderDetailDto.quantity
        }

        return customerOrderDetailRepository.save(newDetail).toResponse()
    }

    fun deleteOrderDetail(orderId: UUID, dishId: UUID) {
        if (!customerOrderDetailRepository.existsById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)){
            throw ElementNotFoundException(objectName = "Order detail", id = "Order ID: $orderId and Dish ID: $dishId")
        }

        return customerOrderDetailRepository.deleteById_CustomerOrder_IdAndId_Dish_Id(orderId, dishId)
    }
}