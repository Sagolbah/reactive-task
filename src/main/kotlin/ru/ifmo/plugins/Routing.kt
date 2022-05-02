package ru.ifmo.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.ifmo.db.MarketRepository
import ru.ifmo.db.entities.Currency
import ru.ifmo.db.entities.Product


fun Application.configureRouting(repository: MarketRepository) {

    // route in coroutine scopes for async
    routing {

        post("/register") {
            val currencyParam = call.request.queryParameters["currency"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                "Missing currency"
            )
            repository.register(Currency.valueOf(currencyParam))
        }

        get("/user/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "No id provided")
            call.respond(Json.encodeToString(repository.getUserById(id.toInt())))
        }

        post("/newProduct") {
            val price = call.request.queryParameters["price"]?.toInt()
            val name = call.request.queryParameters["name"]
            val currency = call.request.queryParameters["currency"]
            if (price == null || name == null || currency == null) {
                return@post call.respond(HttpStatusCode.BadRequest, "Price, name or currency not found")
            }
            repository.addProduct(name, price, Currency.valueOf(currency))
        }

        get("/products/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "No id provided")
            val user =
                repository.getUserById(id.toInt()) ?: return@get call.respond(HttpStatusCode.BadRequest, "Unknown user")
            call.respond(Json.encodeToString(repository.getProductsForUser(user)))
        }

    }
}
