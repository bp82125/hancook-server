package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EmployeeRepository : JpaRepository<Employee, UUID>