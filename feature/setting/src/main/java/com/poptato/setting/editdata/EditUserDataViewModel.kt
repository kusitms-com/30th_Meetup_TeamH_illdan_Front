package com.poptato.setting.editdata

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditUserDataViewModel @Inject constructor(

) : BaseViewModel<EditUserDataPageState>(
    EditUserDataPageState()
) {

    fun onValueChange(newValue: String) {
        updateState(
            uiState.value.copy(
                textInput = newValue
            )
        )
        Timber.d("[테스트] 닉네입 편집 -> ${uiState.value.textInput}")
    }
}