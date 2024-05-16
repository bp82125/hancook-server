package com.hancook.hancookbe.models

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.*

@Entity
@Table(name = "positions")
class Position (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "position_id")
    val id: UUID?,

    @Column(name = "position_name", nullable = false)
    var positionName: String,

    @Column(name = "salary_coefficient", nullable = false)
    var salaryCoefficient: Double,

    @OneToMany(
        mappedBy = "position",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        fetch = FetchType.LAZY,
        targetEntity = Employee::class)
    var employees: List<Employee>,

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false
) {

    fun getNumberOfEmployees(): Int = employees.filter { !it.deleted }.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        if (id != other.id) return false
        if (positionName != other.positionName) return false
        if (salaryCoefficient != other.salaryCoefficient) return false
        if (employees != other.employees) return false
        if (deleted != other.deleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + positionName.hashCode()
        result = 31 * result + salaryCoefficient.hashCode()
        result = 31 * result + employees.hashCode()
        result = 31 * result + deleted.hashCode()
        return result
    }

    override fun toString(): String {
        return "Position(id=$id, positionName='$positionName', salaryCoefficient=$salaryCoefficient, employees=$employees, deleted=$deleted)"
    }
}