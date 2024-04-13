package com.hancook.hancookbe.models

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.*

@Entity
@Table(name = "dishes")
class Dish (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    @Column(name = "dish_id")
    val id: UUID?,

    @Column(name = "dish_name", nullable = false)
    var dishName: String,

    @Column(name = "price", nullable = false)
    var price: Long = 0,

    @Column(name = "image_path", nullable = false)
    var imagePath: String,

    @ManyToOne
    @JoinColumn(name = "dish_type_id", nullable = false)
    var dishType: DishType,

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dish) return false

        if (id != other.id) return false
        if (dishName != other.dishName) return false
        if (price != other.price) return false
        if (imagePath != other.imagePath) return false
        if (dishType != other.dishType) return false
        if (deleted != other.deleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + dishName.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + imagePath.hashCode()
        result = 31 * result + dishType.hashCode()
        result = 31 * result + deleted.hashCode()
        return result
    }

    override fun toString(): String {
        return "Dish(id=$id, dishName='$dishName', price=$price, imagePath='$imagePath', dishType=$dishType, deleted=$deleted)"
    }

}
