package com.poptato.yesterdaylist.allcheck

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.poptato.design_system.Gray100
import com.poptato.design_system.R
import kotlinx.coroutines.delay

@Composable
fun AllCheckScreen(
    goBackToBacklog: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        delay(2500L)
        goBackToBacklog()
    }

    AllCheckContent()
}

@Composable
fun AllCheckContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_all_check_bg),
            contentDescription = "ic_all_check_bg",
            modifier = Modifier.fillMaxSize().fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAllCheck() {
    AllCheckContent()
}