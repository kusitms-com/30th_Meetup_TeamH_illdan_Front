package com.poptato.login

import androidx.lifecycle.viewModelScope
import com.poptato.domain.repository.AuthRepository
import com.poptato.domain.model.response.login.AuthModel
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<KaKaoLoginPageState>(KaKaoLoginPageState()) {

    fun kakaoLogin(token: String) {
        viewModelScope.launch {
            authRepository.login(token).collect {
                resultResponse(it, ::onSuccessKaKaoLogin)
            }
        }
        emitEventFlow(KaKaoLoginEvent.GoToBacklog)
    }

    private fun onSuccessKaKaoLogin(model: AuthModel) {
        Timber.e(model.toString())
    }
}