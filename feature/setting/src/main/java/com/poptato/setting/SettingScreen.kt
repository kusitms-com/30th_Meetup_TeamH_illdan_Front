package com.poptato.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100

@Composable
fun SettingScreen() {

    val viewModel: SettingViewModel = hiltViewModel()
    val uiState: SettingPageState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingContent()
}

@Composable
fun SettingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {

        Text(
            text = "설정",
            color = Gray00
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    SettingContent()
}