package com.poptato.mypage

import com.poptato.domain.model.response.mypage.UserDataModel
import com.poptato.ui.base.PageState

data class MyPagePageState(
    val userDataModel: UserDataModel = UserDataModel(),
    val webViewState: Boolean = false
) : PageState