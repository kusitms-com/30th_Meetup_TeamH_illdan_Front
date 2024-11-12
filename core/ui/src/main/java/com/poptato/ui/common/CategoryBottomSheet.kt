package com.poptato.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poptato.design_system.Gray80
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
        Box(
            modifier = Modifier
                .padding(12.dp)
                .height(4.dp)
                .width(64.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Gray80)
                .align(Alignment.CenterHorizontally)
        ) {}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategoryBottomSheet() {
    CategoryBottomSheetContent()
}