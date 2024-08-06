package example.com.repository.login

import example.com.request.AuthenticateRequest
import example.com.response.ApiResponse
import example.com.response.AuthenticateResponse

class LoginService(private val userRepository: LoginRepository) {
    suspend fun authenticate(credentials: AuthenticateRequest): ApiResponse<AuthenticateResponse?> {
        return try {
            val user = userRepository.authenticate(credentials.idToken)
            ApiResponse(success = true, message = "User registred successfuly", user.data)
        } catch (e: Exception) {
            ApiResponse(success = false, message = e.message ?: "Unknown error", null)
        }
    }
}