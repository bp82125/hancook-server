package com.hancook.hancookbe.enums

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hancook.hancookbe.utils.GenderDeserializer
import com.hancook.hancookbe.utils.GenderSerializer

@JsonSerialize(using = GenderSerializer::class)
@JsonDeserialize(using = GenderDeserializer::class)
enum class Gender {
    MALE,
    FEMALE,
    OTHER;
}