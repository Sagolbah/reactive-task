package ru.ifmo.db

import ru.ifmo.db.entities.Currency
import ru.ifmo.db.entities.Product
import ru.ifmo.db.entities.User


/**
 * Coroutine-based market repository
 * Provides async database operations
 */
interface MarketRepository {
    /**
     * Registers new user
     * @param currency desired currency
     */
    suspend fun register(currency: Currency)

    /**
     * Receive user by ID
     * @param id user id
     * @return user if it exists, null otherwise
     */
    suspend fun getUserById(id: Int): User?

    /**
     * Adds new product
     * @param name
     * @param price
     * @param currency
     */
    suspend fun addProduct(name: String, price: Int, currency: Currency)

    /**
     * Receives all products for user (filtered by desired currency)
     * @param user
     * @return list of products with price in desired currency
     */
    suspend fun getProductsForUser(user: User): List<Product>
}
