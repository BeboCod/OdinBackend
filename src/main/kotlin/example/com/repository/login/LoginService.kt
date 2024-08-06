package example.com.repository.login

import example.com.request.AuthenticateRequest
import example.com.response.ApiResponse
import example.com.response.AuthenticateResponse

class LoginService(private val userRepository: LoginRepository) {
    suspend fun authenticate(credentials: AuthenticateRequest): ApiResponse<AuthenticateResponse?> {
        return try {
            ApiResponse(success = true, message = "User registred successfuly", userRepository.authenticate(credentials.idToken).data)
        } catch (e: Exception) {
            ApiResponse(success = false, message = e.message ?: "Unknown error", null)
        }
    }
}