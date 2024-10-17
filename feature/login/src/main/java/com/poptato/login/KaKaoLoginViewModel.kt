package com.poptato.login

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.request.KaKaoLoginRequest
import com.poptato.domain.model.response.auth.TokenModel
import com.poptato.domain.model.response.login.AuthModel
import com.poptato.domain.usecase.PostKaKaoLoginUseCase
import com.poptato.domain.usecase.auth.SaveTokenUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val postKaKaoLoginUseCase: PostKaKaoLoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : BaseViewModel<KaKaoLoginPageState>(KaKaoLoginPageState()) {

    fun kakaoLogin(token: String) {
        viewModelScope.launch {
            postKaKaoLoginUseCase.invoke(request = KaKaoLoginRequest(token)).collect {
                resultResponse(it, ::onSuccessKaKaoLogin)
            }
        }
    }

    private fun onSuccessKaKaoLogin(model: AuthModel) {
        viewModelScope.launch {
            saveTokenUseCase.invoke(
                request = TokenModel(accessToken = model.accessToken, refreshToken = model.refreshToken)
            ).collect {
                resultResponse(it, {})
            }
        }
        emitEventFlow(KaKaoLoginEvent.GoToBacklog)
    }
}