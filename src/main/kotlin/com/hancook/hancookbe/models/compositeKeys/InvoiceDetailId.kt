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
): Serializable