package example.com.plugins

import example.com.repository.login.LoginRepository
import example.com.repository.login.LoginService
import example.com.request.AuthenticateRequest
import example.com.response.AuthenticateResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("odin/api/v0"){
            route("/auth"){
                post<AuthenticateRequest> {
                    try {
                        val request = call.receive<AuthenticateRequest>()
                        val apiResponse = (LoginService(LoginRepository())).authenticate(request)
                        if (apiResponse.success) {
                            call.respond(HttpStatusCode.Accepted, apiResponse.data!!)
                        } else {
                            call.respond(HttpStatusCode.Unauthorized)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
                    }
                }
            }
        }
    }
}
