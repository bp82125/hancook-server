package com.hancook.hancookbe.models

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "expenses")
class Expense(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "expense_id")
    val id: UUID?,

    @Column(name = "expense_name", nullable = false)
    var name: String,

    @Column(name = "amount", nullable = false)
    var amount: Long,

    @Column(name = "note")
    var note: String,

    @Column(name = "created_at", nullable = false)
    val dateTime: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    var employee: Employee
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