package ru.ifmo.db

import org.jetbrains.exposed.dao.id.IntIdTable

// Using varchar instead of enum since postgres has very complicated support of it

object Users : IntIdTable() {
    val currency = varchar("currency", 3)
}

object Products : IntIdTable() {
    val name = varchar("name", 50)
    val price = integer("price")
    val currency = varchar("currency", 3)
}

