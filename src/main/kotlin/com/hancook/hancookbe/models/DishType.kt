package com.hancook.hancookbe.models

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.UUID

@Entity
@Table(name = "dish_types")
class DishType (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "dish_type_id")
    val id: UUID?,

    @Column( nullable = false)
    var dishTypeName: String,

    @OneToMany(
        mappedBy = "dishType",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        fetch = FetchType.LAZY,
        targetEntity = Dish::class
    )
    var dishes: List<Dish> = mutableListOf(),

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false
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

    fun getNumberOfDishes(): Int {
        return this.dishes.filter { !it.deleted }.size
    }
}