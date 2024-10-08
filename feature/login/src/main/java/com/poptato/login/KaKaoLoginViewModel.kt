package com.poptato.login

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(

) : BaseViewModel<KaKaoLoginPageState>(KaKaoLoginPageState()) {

    fun kakaoLogin() {
        emitEventFlow(KaKaoLoginEvent.GoToBacklog)
    }
}