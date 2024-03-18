package com.hancook.hancookbe.system

import com.hancook.hancookbe.exceptions.*
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AccountStatusException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.security.access.AccessDeniedException

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(ElementNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleElementNotFoundException(ex: ElementNotFoundException) : ResponseEntity<ApiResponse<Map<String, String>>>{
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse(success = false, statusCode = HttpStatus.NOT_FOUND.value(), message = ex.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class, ValidationException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Map<String, String>>> {
        val errorsMap = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage!! }
        return ResponseEntity
            .badRequest()
            .body(ApiResponse(success = false, statusCode = HttpStatus.BAD_REQUEST.value(), data = errorsMap, message = "Provided arguments are invalid, see data for details."))
    }

    @ExceptionHandler(value = [UsernameNotFoundException::class, BadCredentialsException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthenticationException(ex: Exception) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse(success = false, statusCode = HttpStatus.UNAUTHORIZED.value(), data = ex.message ,message = "Username or password is incorrect."))
    }

    @ExceptionHandler(AccountStatusException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAccountException(ex: AccountStatusException) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse(success = false, statusCode = HttpStatus.UNAUTHORIZED.value(), data = ex.message, message = "User account is abnormal."))
    }

    @ExceptionHandler(InvalidBearerTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidBearerTokenException(ex: InvalidBearerTokenException) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse(success = false, statusCode = HttpStatus.UNAUTHORIZED.value(), data = ex.message ,message = "The access token provided is expired, revoked, malformed, invalid for other reasons."))
    }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(ex: AccessDeniedException) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ApiResponse(success = false, statusCode = HttpStatus.FORBIDDEN.value(), data = ex.message, message = "No permission."))
    }

    @ExceptionHandler(InvalidPasswordException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidPasswordException(ex: InvalidPasswordException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse(success = false, statusCode = HttpStatus.UNAUTHORIZED.value(), data = ex.message, message = "Invalid password provided."))
    }

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUsernameAlreadyExistsException(ex: UsernameAlreadyExistsException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse(
                success = false,
                statusCode = HttpStatus.BAD_REQUEST.value(),
                data = null,
                message = ex.message ?: "Username already exists"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleOtherException(ex: Exception) : ResponseEntity<ApiResponse<String>> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse(success = false, statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(), data = ex.message ,message = "An internal server error occurs."))
    }

    @ExceptionHandler(AssociatedEntitiesException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAssociatedEntitiesException(ex: Exception): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse(
                success = false,
                statusCode = HttpStatus.CONFLICT.value(),
                data = null,
                message = ex.message
            )
        )
    }

    @ExceptionHandler(EntityAlreadyAssociatedException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleEntityAlreadyAssociatedException(ex: EntityAlreadyAssociatedException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse(
                success = false,
                statusCode = HttpStatus.CONFLICT.value(),
                data = null,
                message = ex.message
            )
        )
    }
}