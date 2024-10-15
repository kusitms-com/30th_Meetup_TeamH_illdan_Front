package com.poptato.domain.repository

import com.poptato.domain.model.response.login.AuthModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request: String): Flow<Result<AuthModel>>
}