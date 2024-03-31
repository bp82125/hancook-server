package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestCustomerOrderDto
import com.hancook.hancookbe.dtos.ResponseCustomerOrderDto
import com.hancook.hancookbe.exceptions.AssociatedEntityNotFoundException
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.exceptions.EntityAlreadyAssociatedException
import com.hancook.hancookbe.repositories.EmployeeRepository
import com.hancook.hancookbe.repositories.CustomerOrderRepository
import com.hancook.hancookbe.repositories.TableRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class CustomerOrderService(
    @Autowired private val customerOrderRepository: CustomerOrderRepository,
    @Autowired private val tableRepository: TableRepository,
    @Autowired private val employeeRepository: EmployeeRepository
) {
    fun findAllCustomerOrders(): List<ResponseCustomerOrderDto> {
        return customerOrderRepository.findAll().map { it.toResponse() }
    }

    fun findCustomerOrderByTableId(tableId: UUID): ResponseCustomerOrderDto {
        val table = tableRepository
            .findById(tableId)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = tableId.toString()) }

        val order = table.customerOrder
            ?: throw AssociatedEntityNotFoundException(entityName = "Order", association = "Table", id = tableId.toString())

        return order.toResponse()
    }

    fun createCustomerOrder(tableId: UUID, requestCustomerOrderDto: RequestCustomerOrderDto): ResponseCustomerOrderDto {
        val table = tableRepository
            .findById(tableId)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = tableId.toString()) }

        if(table.customerOrder != null){
            throw EntityAlreadyAssociatedException(
                entityName = "Table",
                entityId = table.id.toString(),
                associatedEntityName = "Order",
                associatedEntityId = table.customerOrder?.id.toString()
            )
        }

        val employee = employeeRepository
            .findById(requestCustomerOrderDto.employeeId)
            .orElseThrow { ElementNotFoundException(objectName = "Employee", id = requestCustomerOrderDto.employeeId.toString()) }

        val order = requestCustomerOrderDto.toEntity(table = table, employee = employee)
        return customerOrderRepository.save(order).toResponse()
    }

    fun deleteCustomerOrder(tableId: UUID){
        val table = tableRepository
            .findById(tableId)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = tableId.toString()) }

        val order = table.customerOrder
            ?: throw AssociatedEntityNotFoundException(entityName = "Order", association = "Table", id = tableId.toString())

        table.removeOrder()
        order.id?.let { customerOrderRepository.deleteById(it) }
    }
}