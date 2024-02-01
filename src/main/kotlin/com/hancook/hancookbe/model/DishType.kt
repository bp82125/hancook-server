package com.hancook.hancookbe.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class DishType (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var dishTypeName: String,

    @OneToMany(mappedBy = "dishType", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, targetEntity = Dish::class)
    @JsonManagedReference
    var dishes: List<Dish> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DishType) return false

        if (id != other.id) return false
        if (dishTypeName != other.dishTypeName) return false
        if (dishes != other.dishes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + dishTypeName.hashCode()
        result = 31 * result + dishes.hashCode()
        return result
    }
}