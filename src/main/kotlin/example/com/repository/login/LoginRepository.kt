package example.com.repository.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import example.com.response.ApiResponse
import example.com.response.AuthenticateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository : LoginInterface {

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

                val response = AuthenticateResponse(
                    id = id,
                    name = userToken.name,
                    imageUrl = userToken.picture,
                    verification = userToken.claims["verified"].toString().toBoolean(),
                    followers = document.get("followers") as? List<String> ?: emptyList(),
                    posts = document.get("post") as? List<String> ?: emptyList(),
                    comments = document.get("comments") as? List<String> ?: emptyList()
                )

                ApiResponse(success = true, message = "User authenticate successfully", response)
            } catch (e: Exception) {
                ApiResponse(success = false, message = e.message ?: "Unknown error")
            }
        }
    }
}