package com.hancook.hancookbe.models

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "invoices")
class Invoice (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "invoice_id")
    var id: UUID?,

    @Column(nullable = false)
    var createdTime: LocalDateTime,

    @Column(nullable = false)
    var customerPayment: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    var employee: Employee?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    var table: com.hancook.hancookbe.models.Table?,

    @OneToMany(
        mappedBy = "id.invoice",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        targetEntity = InvoiceDetail::class
    )
    var details: List<InvoiceDetail> = mutableListOf()
) {

    fun calculateTotalPrice(): Long {
        val subtotal = details.sumOf { it.calculateSubtotal() }
        val tax = (subtotal * 0.1).toLong()
        return subtotal + tax
    }

    fun calculateChange(): Long {
        return customerPayment - calculateTotalPrice()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Invoice) return false

        if (id != other.id) return false
        if (createdTime != other.createdTime) return false
        if (customerPayment != other.customerPayment) return false
        if (employee != other.employee) return false
        if (table != other.table) return false
        if (details != other.details) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + createdTime.hashCode()
        result = 31 * result + customerPayment.hashCode()
        result = 31 * result + employee.hashCode()
        result = 31 * result + (table?.hashCode() ?: 0)
        result = 31 * result + details.hashCode()
        return result
    }

    override fun toString(): String {
        return "Invoice(id=$id, createdTime=$createdTime, customerPayment=$customerPayment, employee=$employee, table=$table, details=$details)"
    }


}