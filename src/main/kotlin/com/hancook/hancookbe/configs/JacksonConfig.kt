package com.hancook.hancookbe.configs

import com.fasterxml.jackson.databind.module.SimpleModule
import com.hancook.hancookbe.enums.Gender
import com.hancook.hancookbe.enums.Role
import com.hancook.hancookbe.utils.GenderDeserializer
import com.hancook.hancookbe.utils.GenderSerializer
import com.hancook.hancookbe.utils.RoleDeserializer
import com.hancook.hancookbe.utils.RoleSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Bean
    fun genderModule(): SimpleModule {
        val module = SimpleModule()
        module.addSerializer(Gender::class.java, GenderSerializer())
        module.addDeserializer(Gender::class.java, GenderDeserializer())
        return module
    }

    @Bean
    fun roleModule(): SimpleModule {
        val module = SimpleModule()
        module.addSerializer(Role::class.java, RoleSerializer())
        module.addDeserializer(Role::class.java, RoleDeserializer())
        return module
    }
}