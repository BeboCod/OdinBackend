package example.com.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateRequest(val idToken: String)