package com.hancook.hancookbe.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173") // You can specify specific origins instead of "*"
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify the allowed HTTP methods
                    .allowedHeaders("*") // You can specify specific headers if needed
            }
        }
    }
}