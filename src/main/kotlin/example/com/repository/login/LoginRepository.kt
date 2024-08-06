package example.com.repository.login

import com.google.cloud.firestore.FirestoreException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord.CreateRequest
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

    suspend fun createPrueba(): ApiResponse<String?> {
        return withContext(Dispatchers.IO) {
            try {
                val decryptedEmail = "Christopher.cop787@gmail.com"
                val decryptedPassword = "cris2004"

                // Create Firebase user
                val createRequest = CreateRequest()
                    .setEmail(decryptedEmail)
                    .setPassword(decryptedPassword)

                val userRecord = auth.createUser(createRequest)
                val userId = userRecord.uid
                ApiResponse(success = true, message = "User create successfully", userId)
            } catch (e: FirebaseAuthException) {
                ApiResponse(success = false, message = e.message ?: "Unknown error")
            } catch (e: FirestoreException) {
                ApiResponse(success = false, message = e.message ?: "Unknown error")
            } catch (e: Exception) {
                ApiResponse(success = false, message = e.message ?: "Unknown error")
            }
        }
    }
}