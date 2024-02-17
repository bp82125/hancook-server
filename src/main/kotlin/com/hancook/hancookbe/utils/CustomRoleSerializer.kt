package com.hancook.hancookbe.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.hancook.hancookbe.enums.Role
import java.io.IOException
import java.util.*

class RoleSerializer : JsonSerializer<Role>() {
    @Throws(IOException::class)
    override fun serialize(
        role: Role,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        jsonGenerator.writeString(role.name.lowercase(Locale.getDefault())) // Serialize as lowercase
    }
}

class RoleDeserializer : JsonDeserializer<Role>() {
    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): Role {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)
        val roleString = node.textValue()
        return when (roleString.lowercase(Locale.getDefault())) { // Deserialize from lowercase
            "staff" -> Role.STAFF
            "admin" -> Role.ADMIN
            else -> throw IllegalArgumentException("Invalid role value: $roleString")
        }
    }
}