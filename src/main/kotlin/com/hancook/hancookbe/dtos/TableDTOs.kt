package com.hancook.hancookbe.dtos

import com.hancook.hancookbe.enums.TableState
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class RequestTableDto(
    @field:NotBlank(message = "Name must not be blank")
    val name: String
)

data class ResponseTableDto(
    val id: UUID?,
    val name: String,
    val state: TableState
)