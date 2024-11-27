package com.poptato.splash

import androidx.lifecycle.viewModelScope
import com.poptato.domain.base.ApiException
import com.poptato.domain.model.request.ReissueRequestModel
import com.poptato.domain.model.request.today.GetTodayListRequestModel
import com.poptato.domain.model.response.auth.TokenModel
import com.poptato.domain.model.response.today.TodayListModel
import com.poptato.domain.usecase.auth.GetTokenUseCase
import com.poptato.domain.usecase.auth.ReissueTokenUseCase
import com.poptato.domain.usecase.auth.SaveTokenUseCase
import com.poptato.domain.usecase.today.GetTodayListUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : BaseViewModel<SplashPageState>(SplashPageState()) {
    init {
        checkLocalToken()
    }

    private fun checkLocalToken() {
        viewModelScope.launch {
            getTokenUseCase.invoke(Unit).collect {
                if (it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()) {
                    updateState(uiState.value.copy(skipLogin = true))
                }
            }
        }
    }
}