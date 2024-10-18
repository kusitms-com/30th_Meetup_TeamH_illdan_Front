package com.poptato.setting.editdata

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditUserDataScreen() {

    val viewModel: EditUserDataViewModel = hiltViewModel()
    val uiState: EditUserDataPageState by viewModel.uiState.collectAsStateWithLifecycle()

    EditUserDataContent()
}

@Composable
fun EditUserDataContent() {
    //
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    EditUserDataContent()
}