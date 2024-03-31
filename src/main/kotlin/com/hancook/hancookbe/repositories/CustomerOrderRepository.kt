package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.CustomerOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerOrderRepository : JpaRepository<CustomerOrder, UUID>