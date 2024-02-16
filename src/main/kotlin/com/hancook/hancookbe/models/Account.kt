package com.hancook.hancookbe.models

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

    @OneToOne(mappedBy = "account")
    var employee: Employee
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (employee != other.employee) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (employee.hashCode())
        return result
    }
    
}