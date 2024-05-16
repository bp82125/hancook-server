package com.hancook.hancookbe.exceptions

class DeleteAdminAccountException(username: String) : RuntimeException("Cannot delete administrative account with username: $username")