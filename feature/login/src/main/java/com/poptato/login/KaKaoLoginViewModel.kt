package com.poptato.login

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.request.KaKaoLoginRequest
import com.poptato.domain.model.request.today.GetTodayListRequestModel
import com.poptato.domain.model.response.auth.TokenModel
import com.poptato.domain.model.response.login.AuthModel
import com.poptato.domain.model.response.today.TodayListModel
import com.poptato.domain.usecase.PostKaKaoLoginUseCase
import com.poptato.domain.usecase.auth.SaveTokenUseCase
import com.poptato.domain.usecase.today.GetTodayListUseCase
import com.poptato.ui.base.BaseViewModel
import com.poptato.ui.base.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KaKaoLoginViewModel @Inject constructor(
    private val postKaKaoLoginUseCase: PostKaKaoLoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : BaseViewModel<PageState.Default>(PageState.Default) {

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
        if (model.isNewUser) emitEventFlow(KaKaoLoginEvent.NewUserLogin)
        else emitEventFlow(KaKaoLoginEvent.OnSuccessLogin)
    }
}