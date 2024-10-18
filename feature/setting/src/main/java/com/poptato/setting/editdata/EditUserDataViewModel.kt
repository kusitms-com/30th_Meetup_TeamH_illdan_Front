package com.poptato.setting.editdata

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.response.mypage.UserDataModel
import com.poptato.domain.usecase.mypage.GetUserDataUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditUserDataViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
) : BaseViewModel<EditUserDataPageState>(
    EditUserDataPageState()
) {

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserDataUseCase(request = Unit).collect {
                resultResponse(it, ::setMappingToUserName)
            }
        }
    }

    private fun setMappingToUserName(response: UserDataModel) {
        updateState(
            uiState.value.copy(
                name = response.name
            )
        )
    }

    fun onValueChange(newValue: String) {
        updateState(
            uiState.value.copy(
                name = newValue
            )
        )
        Timber.d("[테스트] 닉네입 편집 -> ${uiState.value.name}")
    }
}