package com.poptato.yesterdaylist

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun YesterdayListScreen() {

    val viewModel: YesterdayListViewModel = hiltViewModel()
    val uiState: YesterdayListPageState by viewModel.uiState.collectAsStateWithLifecycle()

    YesterdayContent(
        uiState = uiState
    )
}

@Composable
fun YesterdayContent(
    uiState: YesterdayListPageState = YesterdayListPageState(),
) {
    Text(text = "테스트")
}