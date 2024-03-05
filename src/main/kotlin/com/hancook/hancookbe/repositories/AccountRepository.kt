package com.hancook.hancookbe.repositories

import com.hancook.hancookbe.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Optional<Account>
}