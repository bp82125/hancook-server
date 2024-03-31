package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.RequestTableDto
import com.hancook.hancookbe.dtos.ResponseTableDto
import com.hancook.hancookbe.models.Table
import java.util.UUID

fun RequestTableDto.toEntity(id: UUID? = null): Table {
    return Table(
        id = id,
        name = this.name,
    )
}

fun Table.toResponse(): ResponseTableDto {
    return ResponseTableDto(
        id = this.id,
        name = this.name,
        state = this.getTableState(),
        orderId = this.customerOrder?.id
    )
}