package example.com.plugins

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.google.cloud.firestore.Firestore
import com.google.firebase.database.FirebaseDatabase
import example.com.FirebaseAdmin.FirebaseAdmin
import example.com.repository.login.LoginRepository

fun Application.configureRouting() {
    routing  {
        route("odin/api/v0"){
            route("/auth"){

            }
        }
    }
}
