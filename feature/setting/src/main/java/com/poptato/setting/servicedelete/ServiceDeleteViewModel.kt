package com.poptato.setting.servicedelete

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.enums.UserDeleteType
import com.poptato.domain.usecase.auth.ClearTokenUseCase
import com.poptato.domain.usecase.mypage.UserDeleteUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ServiceDeleteViewModel @Inject constructor(
    private val userDeleteUseCase: UserDeleteUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : BaseViewModel<ServiceDeletePageState>(
    ServiceDeletePageState()
) {

    fun setSelectedReason(reason: UserDeleteType) {
        val newList: MutableList<UserDeleteType> = mutableListOf()
        newList.addAll(uiState.value.selectedReasonList)

        if (!uiState.value.selectedReasonList.contains(reason)) {
            newList.add(reason)
        } else {
            newList.remove(reason)
        }

        updateState(
            uiState.value.copy(
                selectedReasonList = newList
            )
        )
    }

    fun userDelete() {
        viewModelScope.launch {
            userDeleteUseCase(request = Unit).collect {
                resultResponse(it, {
                    clearTokenUseCase
                    emitEventFlow(ServiceDeleteEvent.GoBackToLogIn)
                }, { error ->
                    Timber.d("[마이페이지] 회원탈퇴 서버통신 실패 -> ${error.message}")
                })
            }
        }
    }
}