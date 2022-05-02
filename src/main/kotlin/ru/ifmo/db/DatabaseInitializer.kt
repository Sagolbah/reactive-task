package ru.ifmo.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseInitializer {
    fun initDatabase() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/market",
            driver = "org.postgresql.Driver",
            user = "sagolbah",
            password = ""
        )
        transaction {
            SchemaUtils.create(Users, Products)
        }
    }
}