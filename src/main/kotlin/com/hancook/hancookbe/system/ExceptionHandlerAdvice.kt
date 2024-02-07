package com.hancook.hancookbe.system

import com.hancook.hancookbe.dto.ResponseDishDto
import com.hancook.hancookbe.dto.ResponseDishTypeDto
import com.hancook.hancookbe.exception.DishNotFoundException
import com.hancook.hancookbe.exception.DishTypeNotFoundException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(DishNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDishNotFoundException(ex: DishNotFoundException) : ResponseEntity<ApiResponse<Map<String, String>>>{
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse(success = false, message = ex.message))
    }

    @ExceptionHandler(DishTypeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDishTypeNotFoundException(ex: DishTypeNotFoundException) : ResponseEntity<ApiResponse<Map<String, String>>>{
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse(success = false, message = ex.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class, ValidationException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Map<String, String>>> {
        val errorsMap = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage!! }
        return ResponseEntity
            .badRequest()
            .body(ApiResponse(success = false, data = errorsMap, message = "Provided arguments are invalid, see data for details."))
    }
}