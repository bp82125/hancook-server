package com.hancook.hancookbe.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType
import java.util.*

@Entity
class Import(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcType(VarcharJdbcType::class)
    val id: UUID?,

    
) {
}