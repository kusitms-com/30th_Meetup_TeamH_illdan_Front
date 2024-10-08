package com.poptato.today

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TodayScreen(

) {
    val viewModel: TodayViewModel = hiltViewModel()

    TodayContent()
}

@Composable
fun TodayContent() {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewToday() {

}