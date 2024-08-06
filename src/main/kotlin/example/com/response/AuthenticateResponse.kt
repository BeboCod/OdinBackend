package example.com.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateResponse(
    val id: String,
    val name: String,
    val imageUrl: String,
    val verification: Boolean,
    val followers: List<String>?,
    val posts: List<String>?,
    val comments: List<String>?
)