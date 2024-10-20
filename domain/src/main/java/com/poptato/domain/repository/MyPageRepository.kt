package com.poptato.domain.repository

import com.poptato.domain.model.response.mypage.UserDataModel
import kotlinx.coroutines.flow.Flow


interface MyPageRepository {

    suspend fun userDelete(): Flow<Result<Unit>>

    suspend fun getUserData(): Flow<Result<UserDataModel>>
}