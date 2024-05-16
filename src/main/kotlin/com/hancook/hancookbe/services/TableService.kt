package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toEntity
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.RequestTableDto
import com.hancook.hancookbe.dtos.ResponseTableDto
import com.hancook.hancookbe.exceptions.ElementNotFoundException
import com.hancook.hancookbe.repositories.TableRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class TableService(
    @Autowired private val tableRepository: TableRepository
) {
    fun findAllTables(): List<ResponseTableDto> {
        return tableRepository.findAllByDeletedFalse().map { it.toResponse() }
    }

    fun findTableById(id: UUID): ResponseTableDto {
        val table = tableRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = id.toString()) }

        return table.toResponse()
    }

    fun createTable(requestTableDto: RequestTableDto): ResponseTableDto {
        val table = requestTableDto.toEntity()
        val createdTable = tableRepository.save(table)
        return createdTable.toResponse()
    }

    fun updateTable(id: UUID, requestTableDto: RequestTableDto): ResponseTableDto {
        val oldTable = tableRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = id.toString()) }

        val newTable = requestTableDto.toEntity(id).apply {
            if (oldTable.customerOrder != null){
                this.customerOrder = oldTable.customerOrder
            }
        }

        val updatedTable = tableRepository.save(newTable)
        return updatedTable.toResponse()
    }

    fun deleteTable(id: UUID){
        val table = tableRepository
            .findById(id)
            .orElseThrow { ElementNotFoundException(objectName = "Table", id = id.toString()) }


        table.deleted = !table.deleted
        tableRepository.save(table)

    }
}