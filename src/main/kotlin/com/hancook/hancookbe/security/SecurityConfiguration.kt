package com.hancook.hancookbe.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {

    @Value("\${api.endpoint.base-url}")
    lateinit var baseUrl: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests {
            it
                .requestMatchers(HttpMethod.GET, "${this.baseUrl}/dishes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "${this.baseUrl}/employees/**").hasAuthority("ROLE_admin")
                .anyRequest().authenticated()
        }
            .csrf { it.disable() }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


}