package com.hancook.hancookbe.enums

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.hancook.hancookbe.utils.RoleDeserializer
import com.hancook.hancookbe.utils.RoleSerializer

@JsonSerialize(using = RoleSerializer::class)
@JsonDeserialize(using = RoleDeserializer::class)
enum class Role {
    STAFF,
    ADMIN
}