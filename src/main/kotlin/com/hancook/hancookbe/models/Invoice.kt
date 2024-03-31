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

    @OneToMany(
        mappedBy = "id.invoice",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        targetEntity = InvoiceDetail::class
    )
    var details: List<InvoiceDetail> = mutableListOf()
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Invoice) return false

        if (id != other.id) return false
        if (createdTime != other.createdTime) return false
        if (customerPayment != other.customerPayment) return false
        if (details != other.details) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + createdTime.hashCode()
        result = 31 * result + customerPayment.hashCode()
        result = 31 * result + details.hashCode()
        return result
    }

    override fun toString(): String {
        return "Invoice(id=$id, createdTime=$createdTime, customerPayment=$customerPayment, details=$details)"
    }

}