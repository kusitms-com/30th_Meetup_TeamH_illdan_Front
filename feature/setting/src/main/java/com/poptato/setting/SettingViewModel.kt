package com.poptato.setting

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

): BaseViewModel<SettingPageState>(
    SettingPageState()
) {
}