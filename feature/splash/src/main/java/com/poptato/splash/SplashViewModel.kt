package com.poptato.splash

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : BaseViewModel<SplashPageState>(
    SplashPageState()
) {

}