package example.com.repository.register

import example.com.request.RegisterRequest
import example.com.response.ApiResponse
import example.com.response.RegisterResponse

class RegisterService(private val registerRepository: RegisterRepository) {
    suspend fun register(request: RegisterRequest): ApiResponse<RegisterResponse?> {
        return try {
            ApiResponse(true, "Success", registerRepository.register(request).data)
        } catch (e: Exception) {
            ApiResponse(false, "Error ${e.printStackTrace()}", null)
        }
    }
}