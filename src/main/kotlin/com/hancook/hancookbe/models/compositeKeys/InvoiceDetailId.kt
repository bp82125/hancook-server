package com.hancook.hancookbe.models.compositeKeys

import com.hancook.hancookbe.models.Dish
import com.hancook.hancookbe.models.Invoice
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.io.Serializable

@Embeddable
class InvoiceDetailId(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    var invoice: Invoice,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", referencedColumnName = "dish_id")
    var dish: Dish,
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InvoiceDetailId) return false

        if (invoice != other.invoice) return false
        if (dish != other.dish) return false

        return true
    }

    override fun hashCode(): Int {
        var result = invoice.hashCode()
        result = 31 * result + dish.hashCode()
        return result
    }

    override fun toString(): String {
        return "InvoiceDetailId(invoice=$invoice, dish=$dish)"
    }
}