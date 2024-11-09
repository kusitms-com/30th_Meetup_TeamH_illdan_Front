package com.poptato.splash

import androidx.lifecycle.viewModelScope
import com.poptato.domain.base.ApiException
import com.poptato.domain.model.request.ReissueRequestModel
import com.poptato.domain.model.response.auth.TokenModel
import com.poptato.domain.usecase.auth.GetTokenUseCase
import com.poptato.domain.usecase.auth.ReissueTokenUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase
) : BaseViewModel<SplashPageState>(SplashPageState()) {
    init {
        checkLocalToken()
    }

    private fun checkLocalToken() {
        viewModelScope.launch {
            getTokenUseCase.invoke(Unit).collect {
                if (it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()) {
                    reissueToken(
                        request = ReissueRequestModel(accessToken = it.accessToken, refreshToken = it.refreshToken)
                    )
                }
            }
        }
    }

    private fun reissueToken(request: ReissueRequestModel) {
        viewModelScope.launch {
            reissueTokenUseCase.invoke(request = request).collect {
                resultResponse(
                    it,
                    { updateState(uiState.value.copy(skipLogin = true)) },
                    { throwable ->
                        if (throwable is ApiException) {
                            when (throwable.code) {
                                6002 -> updateState(uiState.value.copy(skipLogin = false))
                                else -> Timber.e("${throwable.code}")
                            }
                        }
                    }
                )
            }
        }
    }
}