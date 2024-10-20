package com.poptato.setting.userdata

import com.poptato.ui.base.PageState

data class UserDataPageState (
    val temp: String = "",
    var name: String = ""
): PageState