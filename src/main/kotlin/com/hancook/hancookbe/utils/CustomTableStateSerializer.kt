package com.hancook.hancookbe.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.hancook.hancookbe.enums.TableState
import java.io.IOException
import java.util.*

class TableStateSerializer : StdSerializer<TableState>(TableState::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(tableState: TableState, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
        jsonGenerator.writeString(tableState.name.lowercase(Locale.getDefault())) // Serialize as lowercase
    }
}

class TableStateDeserializer : StdDeserializer<TableState>(TableState::class.java) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): TableState {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)
        val tableStateString = node.textValue()
        return when (tableStateString.lowercase(Locale.getDefault())) { // Deserialize from lowercase
            "available" -> TableState.AVAILABLE
            "occupied" -> TableState.OCCUPIED
            else -> throw IllegalArgumentException("Invalid table state value: $tableStateString")
        }
    }
}