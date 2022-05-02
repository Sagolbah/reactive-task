package ru.ifmo.db.entities

@kotlinx.serialization.Serializable
data class Product(val id: Int, val name: String, val price: Int, val currency: Currency)
