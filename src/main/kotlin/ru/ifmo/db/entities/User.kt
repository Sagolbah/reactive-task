package ru.ifmo.db.entities

@kotlinx.serialization.Serializable
data class User(val id: Int, val currency: Currency)
