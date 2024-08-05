package example.com.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(val success: Boolean, val message: String, val data: T? = null)