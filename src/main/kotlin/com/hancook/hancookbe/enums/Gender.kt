package com.hancook.hancookbe.enums

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.util.*

enum class Gender {
    MALE,
    FEMALE,
    OTHER;
}