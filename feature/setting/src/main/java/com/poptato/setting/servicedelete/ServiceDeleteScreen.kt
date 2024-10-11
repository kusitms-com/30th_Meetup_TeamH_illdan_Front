package com.poptato.setting.servicedelete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.poptato.design_system.Gray100
import com.poptato.design_system.Primary60

@Composable
fun ServiceDeleteScreen() {

    ServiceDeleteContent()
}

@Composable
fun ServiceDeleteContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        Text(text = "테스트", color = Primary60)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    ServiceDeleteContent()
}