package com.hancook.hancookbe.dtos

import com.hancook.hancookbe.enums.Role
import com.hancook.hancookbe.models.Account
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class RequestAccountDto(
    @field:NotEmpty(message = "Username is required")
    val username: String,

    @field:NotEmpty(message = "Password is required")
    val password: String,

    @field:NotNull(message = "Role is required")
    val role: Role, // Add role field

    val enabled: Boolean,

    val employeeId: UUID? = null
)

data class ResponseAccountDto(
    val id: UUID?,
    val username: String,
    val role: Role,
    val enabled: Boolean
)

class AccountPrinciple(private val account: Account) : UserDetails {

    override fun getUsername(): String = account.username

    override fun getPassword(): String = account.password

    override fun isEnabled(): Boolean = account.enabled

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(account.role.name))
    }

    fun getAccount(): Account = this.account
}