package com.poptato.onboarding

import com.airbnb.lottie.compose.LottieCompositionSpec
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(

) : BaseViewModel<OnboardingPageState>(OnboardingPageState()) {
    init {
        updateState(
            uiState.value.copy(
                onboardingList = listOf(
                    OnboardingModel(
                        title = "",
                        resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding1)
                    ),
                    OnboardingModel(
                        title = "",
                        resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding2)
                    ),
                    OnboardingModel(
                        title = "",
                        resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding3)
                    ),OnboardingModel(
                        title = "",
                        resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding4)
                    )
                )
            )
        )
    }
}