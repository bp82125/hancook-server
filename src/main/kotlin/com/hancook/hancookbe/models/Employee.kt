package com.hancook.hancookbe.models

import com.hancook.hancookbe.enums.Gender
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.*

@Entity
@Table(name = "employees")
class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "employee_id")
    val id: UUID?,

    @Column(name = "employee_name", nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    var gender: Gender,

    @Column(name = "address", nullable = false)
    var address: String,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @ManyToOne
    @JoinColumn(name = "position_id")
    var position: Position,

    @OneToOne
    @JoinColumn(name = "account_id")
    var account: Account? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (gender != other.gender) return false
        if (address != other.address) return false
        if (phoneNumber != other.phoneNumber) return false
        if (position != other.position) return false
        if (account != other.account) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + account.hashCode()
        return result
    }

    fun removeAccount(account: Account) {
        this.account?.employee = null
        this.account = null

    }

}