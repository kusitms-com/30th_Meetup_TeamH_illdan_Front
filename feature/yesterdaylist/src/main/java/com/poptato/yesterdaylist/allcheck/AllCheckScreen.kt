package com.poptato.yesterdaylist.allcheck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100

@Composable
fun AllCheckScreen() {
    AllCheckContent()
}

@Composable
fun AllCheckContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        Text(text = "완료 테스트", color = Gray00)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAllCheck() {
    AllCheckContent()
}