package com.hancook.hancookbe.enums

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hancook.hancookbe.utils.TableStateDeserializer
import com.hancook.hancookbe.utils.TableStateSerializer

@JsonSerialize(using = TableStateSerializer::class)
@JsonDeserialize(using = TableStateDeserializer::class)
enum class TableState {
    AVAILABLE,
    OCCUPIED
}