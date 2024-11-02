package com.poptato.splash

import androidx.lifecycle.viewModelScope
import com.poptato.domain.usecase.auth.GetTokenUseCase
import com.poptato.ui.base.BaseViewModel
import com.poptato.ui.base.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : BaseViewModel<SplashPageState>(SplashPageState()) {
    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            getTokenUseCase.invoke(Unit).collect {
                if (it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()) {
                    updateState(
                        uiState.value.copy(skipLogin = true)
                    )
                }
            }
        }
    }
}