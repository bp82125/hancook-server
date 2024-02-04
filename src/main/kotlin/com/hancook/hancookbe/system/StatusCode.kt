package com.hancook.hancookbe.system

object StatusCode {
    const val SUCCESS: Int = 200 // Success

    const val INVALID_ARGUMENT: Int = 400 // Bad request, e.g., invalid parameters

    const val UNAUTHORIZED: Int = 401 // Username or password incorrect

    const val FORBIDDEN: Int = 403 // No permission

    const val NOT_FOUND: Int = 404 // Not found

    const val INTERNAL_SERVER_ERROR: Int = 500 // Server internal error
}
