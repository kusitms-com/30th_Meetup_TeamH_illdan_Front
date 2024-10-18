package com.poptato.mypage

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.response.mypage.UserDataModel
import com.poptato.domain.usecase.mypage.GetUserDataUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
) : BaseViewModel<MyPagePageState>(
    MyPagePageState()
) {

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserDataUseCase(request = Unit).collect {
                resultResponse(it, { data ->
                    setMappingToUserData(data)
                    Timber.d("[마이페이지] 유저 정보 서버통신 성공 -> $data")
                }, { error ->
                    Timber.d("[마이페이지] 유저 정보 서버통신 실패 -> $error")
                })
            }
        }
    }

    private fun setMappingToUserData(response: UserDataModel) {
        updateState(
            uiState.value.copy(
                userDataModel = response
            )
        )
    }
}