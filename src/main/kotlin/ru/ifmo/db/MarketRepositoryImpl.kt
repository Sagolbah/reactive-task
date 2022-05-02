package ru.ifmo.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ru.ifmo.db.entities.Currency
import ru.ifmo.db.entities.Product
import ru.ifmo.db.entities.User

class MarketRepositoryImpl : MarketRepository {
    override suspend fun register(currency: Currency) {
        newSuspendedTransaction(Dispatchers.IO) {
            Users.insert {
                it[Users.currency] = currency.name
            }
        }
    }

    override suspend fun getUserById(id: Int): User? {
        return newSuspendedTransaction(Dispatchers.IO) {
            mapToUser(Users.select { Users.id eq id }.firstOrNull())
        }
    }

    override suspend fun addProduct(name: String, price: Int, currency: Currency) {
        newSuspendedTransaction(Dispatchers.IO) {
            Products.insert {
                it[Products.name] = name
                it[Products.price] = price
                it[Products.currency] = currency.name
            }
        }
    }

    override suspend fun getProductsForUser(user: User): List<Product> {
        return newSuspendedTransaction(Dispatchers.IO) {
            Products.select { Products.currency eq user.currency.name }.map { row ->
                mapToProduct(row)
            }
        }
    }

    private fun mapToProduct(row: ResultRow): Product {
        return Product(
            id = row[Products.id].value,
            name = row[Products.name],
            price = row[Products.price],
            currency = Currency.valueOf(row[Products.currency])
        )
    }

    private fun mapToUser(row: ResultRow?): User? {
        if (row == null) return null
        return User(
            id = row[Users.id].value,
            currency = Currency.valueOf(row[Users.currency])
        )
    }
}