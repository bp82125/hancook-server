package com.hancook.hancookbe.system

import com.hancook.hancookbe.exceptions.ElementNotFoundException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(ElementNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleElementNotFoundException(ex: ElementNotFoundException) : ResponseEntity<ApiResponse<Map<String, String>>>{
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