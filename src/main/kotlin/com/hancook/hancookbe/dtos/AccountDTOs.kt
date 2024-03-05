package com.hancook.hancookbe.dtos

import com.hancook.hancookbe.enums.Role
import com.hancook.hancookbe.models.Account
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class CreateAccountDto(
    @field:NotEmpty(message = "Username is required")
    val username: String,

    @field:NotEmpty(message = "Password is required")
    val password: String,

    @field:NotNull(message = "Role is required")
    val role: Role,

    val enabled: Boolean,

    val employeeId: UUID? = null
)

data class UpdateAccountDto(
    @field:NotNull(message = "Role is required")
    val role: Role,
)

data class UpdatePasswordDto(
    @field:NotEmpty(message = "Old password is required")
    val oldPassword: String,

    @field:NotEmpty(message = "New password is required")
    val newPassword: String,
)

data class ResponseAccountDto(
    val id: UUID?,
    val username: String,
    val role: Role,
    val enabled: Boolean,
    val employeeId: UUID? = null,
    val employeeName: String?
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