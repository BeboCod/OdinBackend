package example.com.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val token: TokenResponse, val uid: String)