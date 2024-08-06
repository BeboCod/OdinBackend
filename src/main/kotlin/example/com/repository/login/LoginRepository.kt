package example.com.repository.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.database.FirebaseDatabase
import example.com.response.ApiResponse
import example.com.response.AuthenticateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

class LoginRepository: LoginInterface {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirestoreClient.getFirestore()
    private val usersCollection = firestore.collection("Users")

    override suspend fun authenticate(tokenId: String): ApiResponse<AuthenticateResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val userToken = auth.verifyIdToken(tokenId) ?: return@withContext ApiResponse(
                    success = false,
                    message = "Invalid token"
                )
                val id = userToken.uid
                val document = usersCollection.document(id).get().get() ?: return@withContext ApiResponse(
                    success = false,
                    message = "Invalid token"
                )

                val imageUrl = userToken.picture
                val username = userToken.name
                val verified = userToken.claims["verified"].toString().toBoolean()
                val followers = document.get("followers") as? List<String>//almacena id de usuarios
                val post = document.get("post") as? List<String>//almacena id de posts
                val comments = document.get("comments") as? List<String>//alamcena id de comentarios

                val response = AuthenticateResponse(
                    id = id,
                    name = username,
                    imageUrl = imageUrl,
                    verification = verified,
                    followers = followers,
                    posts = post,
                    comments = comments
                )

                ApiResponse(success = true, message = "User authenticate successfully", response)
            } catch (e: Exception) {
                ApiResponse(success = false, message = e.message ?: "Unknown error")
            }
        }
    }

}