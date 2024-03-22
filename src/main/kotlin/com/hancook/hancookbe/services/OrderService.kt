package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestOrderDto
import com.hancook.hancookbe.dtos.ResponseOrderDto
import com.hancook.hancookbe.exceptions.AssociatedEntityNotFoundException
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.exceptions.EntityAlreadyAssociatedException
import com.hancook.hancookbe.repositories.EmployeeRepository
import com.hancook.hancookbe.repositories.OrderRepository
import com.hancook.hancookbe.repositories.TableRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class OrderService(
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val tableRepository: TableRepository,
    @Autowired private val employeeRepository: EmployeeRepository
) {
    fun findAllOrders(): List<ResponseOrderDto> {
        return orderRepository.findAll().map { it.toResponse() }
    }

    fun findOrderById(orderId: UUID): ResponseOrderDto {
        return orderRepository
            .findById(orderId)
            .map { it.toResponse() }
            .orElseThrow { ElementNotFoundException(objectName = "Order", id = orderId.toString()) }
    }

    fun findOrderByTableId(tableId: UUID): ResponseOrderDto {
        val table = tableRepository
            .findById(tableId)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = tableId.toString()) }

        val order = table.customerOrder
            ?: throw AssociatedEntityNotFoundException(entityName = "Order", association = "Table", id = tableId.toString())

        return order.toResponse()
    }

    fun createOrder(requestOrderDto: RequestOrderDto): ResponseOrderDto {
        val table = tableRepository
            .findById(requestOrderDto.tableId)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = requestOrderDto.tableId.toString()) }

        if(table.customerOrder != null){
            throw EntityAlreadyAssociatedException(
                entityName = "Table",
                entityId = table.id.toString(),
                associatedEntityName = "Order",
                associatedEntityId = table.customerOrder?.id.toString()
            )
        }

        val employee = employeeRepository
            .findById(requestOrderDto.employeeId)
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = requestOrderDto.employeeId.toString()) }

        val order = requestOrderDto.toEntity(table = table, employee = employee)
        return orderRepository.save(order).toResponse()
    }

    fun updateOrder(orderId: UUID, requestOrderDto: RequestOrderDto): ResponseOrderDto {
        val table = tableRepository
            .findById(requestOrderDto.tableId)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = requestOrderDto.tableId.toString()) }

        if(table.customerOrder != null){
            throw EntityAlreadyAssociatedException(
                entityName = "Table",
                entityId = table.id.toString(),
                associatedEntityName = "Order",
                associatedEntityId = table.customerOrder?.id.toString()
            )
        }

        val employee = employeeRepository
            .findById(requestOrderDto.employeeId)
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = requestOrderDto.employeeId.toString()) }

        val oldOrder = orderRepository
            .findById(orderId)
            .orElseThrow { ElementNotFoundException(objectName = "Order", id = orderId.toString()) }

        val newOrder = requestOrderDto.toEntity(
            id = orderId,
            table = table,
            employee = employee
        ).apply { this.details = oldOrder.details }

        return orderRepository.save(newOrder).toResponse()
    }

    fun deleteOrder(orderId: UUID){
        val order = orderRepository
            .findById(orderId)
            .orElseThrow { ElementNotFoundException(objectName = "Order", id = orderId.toString()) }

        order.table?.removeOrder()
        orderRepository.deleteById(orderId)
    }
}