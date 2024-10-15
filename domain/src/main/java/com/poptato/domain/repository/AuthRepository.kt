package com.poptato.domain.repository

import com.poptato.domain.model.request.ReissueRequestModel
import com.poptato.domain.model.response.auth.TokenModel
import com.poptato.domain.model.response.login.AuthModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request: String): Flow<Result<AuthModel>>
    suspend fun reissueToken(request: ReissueRequestModel): Flow<Result<TokenModel>>
}