package com.hancook.hancookbe.models

import com.hancook.hancookbe.models.compositeKeys.OrderDetailId
import jakarta.persistence.*
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "order_details")
class OrderDetail(
    @Id
    var id: OrderDetailId,

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 1,

    @Column(name = "note", nullable = false)
    var note: String? = ""
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderDetail) return false

        if (id != other.id) return false
        if (quantity != other.quantity) return false
        if (note != other.note) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + quantity
        result = 31 * result + (note?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "OrderDetail(orderDetailId=$id, quantity=$quantity, note=$note)"
    }

}