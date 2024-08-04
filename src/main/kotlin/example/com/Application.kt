package example.com


import example.com.FirebaseAdmin.FirebaseAdmin
import example.com.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    FirebaseAdmin.init()
    configureSerialization()
    configureRouting()
}
