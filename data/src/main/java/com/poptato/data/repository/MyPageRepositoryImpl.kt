package com.poptato.data.repository

import com.poptato.data.base.BaseRepository
import com.poptato.data.mapper.UnitResponseMapper
import com.poptato.data.mapper.UserDataResponseMapper
import com.poptato.data.service.MyPageService
import com.poptato.domain.model.response.mypage.UserDataModel
import com.poptato.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageService: MyPageService
) : MyPageRepository, BaseRepository() {

    override suspend fun userDelete(): Flow<Result<Unit>> {
        return apiLaunch(apiCall = { myPageService.userDelete() }, UnitResponseMapper)
    }

    override suspend fun getUserData(): Flow<Result<UserDataModel>> {
        return apiLaunch(apiCall = { myPageService.getUserData() }, UserDataResponseMapper)
    }
}