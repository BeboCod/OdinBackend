package example.com.repository.login

import example.com.response.ApiResponse
import example.com.response.AuthenticateResponse

interface LoginInterface {
    suspend fun authenticate(tokenId: String): ApiResponse<AuthenticateResponse>
}