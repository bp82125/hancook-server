package com.hancook.hancookbe.models

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "customer_orders")
class CustomerOrder (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "customer_order_id")
    var id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    var employee: Employee,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    var table: com.hancook.hancookbe.models.Table?,

    @OneToMany(
        mappedBy = "id.customerOrder",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        targetEntity = OrderDetail::class
    )
    var details: List<OrderDetail> = mutableListOf(),

    @Column(name = "placed_time", nullable = false)
    var orderPlacedTime: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CustomerOrder) return false

        if (id != other.id) return false
        if (employee != other.employee) return false
        if (table != other.table) return false
        if (details != other.details) return false
        if (orderPlacedTime != other.orderPlacedTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + employee.hashCode()
        result = 31 * result + table.hashCode()
        result = 31 * result + details.hashCode()
        result = 31 * result + orderPlacedTime.hashCode()
        return result
    }

    override fun toString(): String {
        return "Order(id=$id, employee=$employee, table=$table, orderDetails=$details, orderPlacedTime=$orderPlacedTime)"
    }
}