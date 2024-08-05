package example.com.repository.login

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.firebase.auth.FirebaseAuth
import example.com.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun authenticate(tokenId: String): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                val userToken = auth.verifyIdToken(tokenId) ?: return@withContext ApiResponse(
                    success = false,
                    message = "Invalid token"
                )
                val id = userToken.uid
                ApiResponse(success = true, message = "User authenticate successfully", "")
            } catch (e: Exception) {
                ApiResponse(success = false, message = e.message ?: "Unknown error")
            }
        }
    }
}