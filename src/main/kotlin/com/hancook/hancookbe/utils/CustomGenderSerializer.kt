package com.hancook.hancookbe.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.hancook.hancookbe.enums.Gender

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException
import java.util.*

class GenderSerializer : StdSerializer<Gender>(Gender::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(gender: Gender, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
        jsonGenerator.writeString(gender.name.lowercase(Locale.getDefault())) // Serialize as lowercase
    }
}

class GenderDeserializer : StdDeserializer<Gender>(Gender::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): Gender {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)
        val genderString = node.textValue()
        return when (genderString.lowercase(Locale.getDefault())) { // Deserialize from lowercase
            "male" -> Gender.MALE
            "female" -> Gender.FEMALE
            "other" -> Gender.OTHER
            else -> throw IllegalArgumentException("Invalid gender value: $genderString")
        }
    }
}