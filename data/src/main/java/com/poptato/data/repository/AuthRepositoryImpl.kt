package com.poptato.data.repository

import com.poptato.data.base.BaseRepository
import com.poptato.data.mapper.AuthResponseMapper
import com.poptato.data.service.AuthService
import com.poptato.domain.repository.AuthRepository
import com.poptato.domain.model.response.login.AuthModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val authService: AuthService
): AuthRepository, BaseRepository() {
    override suspend fun login(request: String): Flow<Result<AuthModel>> {
        return apiLaunch(apiCall = { authService.login(request) }, AuthResponseMapper)
    }
}