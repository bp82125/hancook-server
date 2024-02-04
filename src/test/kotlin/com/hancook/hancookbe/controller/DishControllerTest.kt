package com.hancook.hancookbe.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hancook.hancookbe.model.Dish
import com.hancook.hancookbe.model.DishType
import com.hancook.hancookbe.service.DishServiceImpl
import com.hancook.hancookbe.system.StatusCode
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@WebMvcTest(DishController::class)
@ExtendWith(MockKExtension::class, SpringExtension::class)
class DishControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    @MockkBean
    lateinit var dishServiceImpl: DishServiceImpl

    @Autowired
    private lateinit var objectMapper: ObjectMapper

        private var dishes = listOf(
            Dish(
                id = 1,
                dishName = "Lau Tom Hum",
                price = 500000,
                imagePath = "/test/tomHum",
                dishType = DishType(
                    id = 1,
                    dishTypeName = "Lau"
                )
            ),
            Dish(
                id = 2,
                dishName = "Lau Chua Cay",
                price = 120000,
                imagePath = "/test/chuaCay",
                dishType = DishType(
                    id = 1,
                    dishTypeName = "Lau"
                )
            ),
            Dish(
                id = 3,
                dishName = "Lau Ngot",
                price = 200000,
                imagePath = "/test/keoCon",
                dishType = DishType(
                    id = 1,
                    dishTypeName = "Lau"
                )
            )
        )
    @BeforeEach
    fun setUp() {

    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `should return a specific dish when getDishById endpoint is called with a valid ID`() {
        // Given
        val dishId = 1L
        val dish = dishes.find { it.id == dishId }
        every { dishServiceImpl.getDishById(dishId) } returns dish

        // When
        mockMvc.get("/api/v1/dish/$dishId")

        // Then
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(dish))
                }
            }

        verify(exactly = 1) { dishServiceImpl.getDishById(dishId) }
    }

    @Test
    fun `should return 404 when getDishById endpoint is called without a valid ID`() {
        // Given
        val dishId = 4L
        every { dishServiceImpl.getDishById(dishId) } returns null

        // When
        mockMvc.get("/api/v1/dish/$dishId")
        //Then
            .andExpect { status { isNotFound() } }

        verify(exactly = 1) { dishServiceImpl.getDishById(dishId) }
    }

    @Test
    fun getDishById() {
    }

    @Test
    fun createDish() {
    }

    @Test
    fun updateDish() {
    }

    @Test
    fun deleteDish() {
    }
}