package com.hancook.hancookbe.exceptions

class DeleteAdminAccountEmployeeException(employee: String) : RuntimeException("Cannot delete the employee with the administrative account: $employee")