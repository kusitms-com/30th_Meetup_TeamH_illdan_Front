package com.poptato.setting.editdata

import com.poptato.ui.base.PageState

data class EditUserDataPageState (
    val temp: String = "",
    var textInput: String = "state"
): PageState