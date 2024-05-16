package com.hancook.hancookbe.models

import com.hancook.hancookbe.enums.TableState
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.*

@Entity
@jakarta.persistence.Table(name = "tables")
class Table(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "table_id")
    var id: UUID?,

    @Column(name = "table_name", nullable = false)
    var name: String,

    @OneToOne(mappedBy = "table")
    var customerOrder: CustomerOrder? = null,

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false
) {
    fun getTableState(): TableState {
        return if (customerOrder == null) TableState.AVAILABLE else TableState.OCCUPIED
    }

    fun removeOrder(){
        this.customerOrder?.table = null
        this.customerOrder = null
    }

    override fun toString(): String {
        return "Table(id=$id, name='$name', order=$customerOrder)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Table) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (customerOrder != other.customerOrder) return false
        if (deleted != other.deleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + (customerOrder?.hashCode() ?: 0)
        result = 31 * result + deleted.hashCode()
        return result
    }
}