package com.poptato.backlog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BacklogScreen(

) {
    val viewModel: BacklogViewModel = hiltViewModel()


}

@Composable
fun BacklogContent() {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBacklog() {
    BacklogContent()
}