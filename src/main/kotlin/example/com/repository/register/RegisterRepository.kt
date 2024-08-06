package example.com.repository.register

import com.google.cloud.firestore.FirestoreException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord.CreateRequest
import com.google.firebase.cloud.FirestoreClient
import example.com.request.RegisterRequest
import example.com.response.ApiResponse
import example.com.response.RegisterResponse
import example.com.response.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirestoreClient.getFirestore()
    private val usersCollection = firestore.collection("Users")

    suspend fun register(request: RegisterRequest): ApiResponse<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val createRequest = CreateRequest()
                    .setEmail(request.email)
                    .setPassword(request.password)
                    .setPhotoUrl(request.imageUrl)
                    .setDisplayName(request.name)

                val userRecord = auth.createUser(createRequest)
                val userId = userRecord.uid
                userRecord.customClaims["verified"] = false

                val userAdditionalData: Map<String, Any> = hashMapOf(
                    "userName" to userRecord.displayName,
                    "comments" to ArrayList<String>(),
                    "post" to ArrayList<String>(),
                    "followers" to ArrayList<String>()
                )

                usersCollection.document(userId).set(userAdditionalData)
                val response = RegisterResponse(TokenResponse(userId), userId)

                ApiResponse(success = true, message = "User create successfully", response)
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