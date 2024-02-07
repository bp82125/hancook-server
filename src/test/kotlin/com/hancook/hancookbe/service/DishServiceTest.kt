package com.hancook.hancookbe.service

import com.hancook.hancookbe.model.Dish
import com.hancook.hancookbe.model.DishType
import com.hancook.hancookbe.repository.DishRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every

import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@SpringBootTest
@ExtendWith(MockKExtension::class, SpringExtension::class)
class DishServiceTest {

     @Autowired
     @MockkBean
     lateinit var dishRepository: DishRepository

     @Autowired
     lateinit var dishService: DishService
    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `getDishById should return the dish when ID is found`() {
        // Given
        val a = Dish(
            id = 1,
            dishName = "Lau ga",
            price = 6000000,
            imagePath = "/abc",
            dishType = DishType(
                id = 1,
                dishTypeName = "Lau"
            )
        )
        every { dishRepository.findById(1) } returns Optional.of(a)

        // When
        val returnedDish = dishService.getDishById(1)

        // Then
        assertThat(returnedDish?.dishName).isEqualTo(a.dishName)
        assertThat(returnedDish?.id).isEqualTo(a.id)
        assertThat(returnedDish?.price).isEqualTo(a.price)
        assertThat(returnedDish?.imagePath).isEqualTo(a.imagePath)
        assertThat(returnedDish?.dishType).isEqualTo(a.dishType)

        verify(exactly = 1) { dishRepository.findById(1) }
    }

    @Test
    fun `getDishById should return null when ID is not found`() {
        // Given
        val nonExistingId = 1L
        every { dishRepository.findById(any<Long>()) } returns Optional.empty()

        // When
        val result = dishService.getDishById(nonExistingId)

        // Then
        assertNull(result)
        verify(exactly = 1) { dishRepository.findById(1) }
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
