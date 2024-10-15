package com.poptato.data.service

import com.poptato.data.base.ApiResponse
import com.poptato.data.base.Endpoints
import com.poptato.data.model.response.login.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST(Endpoints.Auth.LOGIN)
    suspend fun login(
        @Body request: String
    ): Response<ApiResponse<AuthResponse>>
}