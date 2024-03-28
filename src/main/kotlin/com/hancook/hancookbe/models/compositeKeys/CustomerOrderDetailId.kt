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
) : Serializable