package com.hancook.hancookbe.models.compositeKeys

import com.hancook.hancookbe.models.CustomerOrder
import com.hancook.hancookbe.models.Dish
import jakarta.persistence.*
import java.io.Serializable

@Embeddable
class CustomerOrderDetailId(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_order_id", referencedColumnName = "customer_order_id")
    var customerOrder: CustomerOrder,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", referencedColumnName = "dish_id")
    var dish: Dish,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CustomerOrderDetailId) return false

        if (customerOrder != other.customerOrder) return false
        if (dish != other.dish) return false

        return true
    }

    override fun hashCode(): Int {
        var result = customerOrder.hashCode()
        result = 31 * result + dish.hashCode()
        return result
    }

    override fun toString(): String {
        return "CustomerOrderDetailId(customerOrder=$customerOrder, dish=$dish)"
    }
}