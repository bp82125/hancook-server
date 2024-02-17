package com.hancook.hancookbe.models

import com.hancook.hancookbe.enums.Role
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.UUID

@Entity
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    val id: UUID?,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,

    var enabled: Boolean = true,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    var employee: Employee?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (enabled != other.enabled) return false
        if (role != other.role) return false
        if (employee != other.employee) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + (employee?.hashCode() ?: 0)
        return result
    }


}