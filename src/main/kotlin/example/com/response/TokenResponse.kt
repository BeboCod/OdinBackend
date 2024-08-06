package example.com.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(val idToken: String)