package com.poptato.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray90

@Composable
fun CategoryBottomSheet() {

    CategoryBottomSheetContent()
}

@Composable
fun CategoryBottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(610.dp)
            .background(Gray90)
    ) {
        Text(text = "테스트", color = Gray00)
    }
}