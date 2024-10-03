package com.poptato.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.poptato.design_system.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    goToKaKaoLogin: () -> Unit = {}
) {
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        delay(2000L)
        goToKaKaoLogin()
    }

    SplashContent()
}

@Composable
fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_temp_splash),
            contentDescription = "ic_splash"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSplash() {
    SplashContent()
}