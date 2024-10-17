package com.poptato.setting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.poptato.setting.logout.LogOutDialogState
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

): BaseViewModel<SettingPageState>(
    SettingPageState()
) {

    val logOutDialogState: MutableState<LogOutDialogState> = mutableStateOf(LogOutDialogState())

    fun showLogOutDialog() {
        logOutDialogState.value = logOutDialogState.value.copy(isShowDialog = true)
    }
}