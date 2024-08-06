package example.com.plugins

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("odin/api/v0") {
            route("/auth") {
                get {
                    call.respond(FreeMarkerContent("auth.html", mapOf("name" to "world")))
                }
            }
        }
    }
}
