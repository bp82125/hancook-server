package com.hancook.hancookbe.models

import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.*

@Entity
class Position (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    val id: UUID?,

    @Column(nullable = false)
    var positionName: String,

    @Column(nullable = false)
    var salaryCoefficient: Double,

    @OneToMany(
        mappedBy = "position",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        fetch = FetchType.LAZY,
        targetEntity = Employee::class)
    var employees: List<Employee>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        if (id != other.id) return false
        if (positionName != other.positionName) return false
        if (salaryCoefficient != other.salaryCoefficient) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + positionName.hashCode()
        result = 31 * result + salaryCoefficient.hashCode()
        return result
    }

    fun getNumberOfEmployees(): Int = employees.size
}