package example.com.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val id: String,
    val email: String,
    val password: String,
    val name: String,
    val imageUrl: String,
    val verification: Boolean,
    val followers: List<String>?,
    val posts: List<String>?,
    val comments: List<String>?
)