package ru.ifmo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.ifmo.db.DatabaseInitializer
import ru.ifmo.db.MarketRepositoryImpl
import ru.ifmo.plugins.*

fun main() {
    DatabaseInitializer().initDatabase()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting(MarketRepositoryImpl())
    }.start(wait = true)
}
