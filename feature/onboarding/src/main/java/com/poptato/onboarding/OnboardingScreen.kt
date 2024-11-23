package com.poptato.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray70
import com.poptato.design_system.Gray95
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.Primary60
import com.poptato.design_system.START

@Composable
fun OnboardingScreen() {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { uiState.onboardingList.size })

    OnboardingContent(
        onboardingList = uiState.onboardingList,
        pagerState = pagerState
    )
}

@Composable
fun OnboardingContent(
    onboardingList: List<OnboardingModel> = emptyList(),
    pagerState: PagerState
) {
    val composition by rememberLottieComposition(onboardingList[pagerState.currentPage].resource)
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        restartOnPlay = true
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = onboardingList[pagerState.currentPage].title,
                style = PoptatoTypo.xxLSemiBold,
                color = Gray00
            )
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f / 1f),
                key = { onboardingList[pagerState.currentPage].title }
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            PageIndicator(
                numberOfPages = pagerState.pageCount
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (pagerState.currentPage == 3) {
                            Primary60
                        } else {
                            Gray95
                        }
                    )
            ) {
                Text(
                    text = START,
                    style = PoptatoTypo.mdSemiBold,
                    color = if (pagerState.currentPage == 3) {
                        Gray100
                    } else {
                        Gray70
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOnboarding() {
//    OnboardingContent(
//        onboardingList = listOf(
//            OnboardingModel(
//                title = "",
//                resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding1)
//            ),
//            OnboardingModel(
//                title = "",
//                resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding2)
//            ),
//            OnboardingModel(
//                title = "",
//                resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding3)
//            ),OnboardingModel(
//                title = "",
//                resource = LottieCompositionSpec.RawRes(com.poptato.design_system.R.raw.onboarding4)
//            )
//        )
//    )
}