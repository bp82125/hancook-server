package com.hancook.hancookbe.models

import com.hancook.hancookbe.models.compositeKeys.InvoiceDetailId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "invoice_details")
class InvoiceDetail(
    @Id
    var id: InvoiceDetailId,

    @Column(name = "unitPrice", nullable = false)
    var unitPrice: Long = 0,

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 1,

    @Column(name = "note", nullable = false)
    var note: String? = ""
): Serializable {
    fun calculateSubtotal(): Long {
        return unitPrice * quantity
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InvoiceDetail) return false

        if (id != other.id) return false
        if (unitPrice != other.unitPrice) return false
        if (quantity != other.quantity) return false
        if (note != other.note) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + unitPrice.hashCode()
        result = 31 * result + quantity
        result = 31 * result + (note?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "InvoiceDetail(id=$id, unitPrice=$unitPrice, quantity=$quantity, note=$note)"
    }

}