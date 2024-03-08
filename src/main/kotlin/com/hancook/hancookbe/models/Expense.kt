package com.hancook.hancookbe.models

import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.time.LocalDateTime
import java.util.*

@Entity
class Expense(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    val id: UUID?,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var amount: Long,

    @Column
    var note: String,

    @Column(nullable = false)
    val dateTime: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Expense) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (amount != other.amount) return false
        if (note != other.note) return false
        if (dateTime != other.dateTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + amount.hashCode()
        result = 31 * result + (note?.hashCode() ?: 0)
        result = 31 * result + dateTime.hashCode()
        return result
    }
}