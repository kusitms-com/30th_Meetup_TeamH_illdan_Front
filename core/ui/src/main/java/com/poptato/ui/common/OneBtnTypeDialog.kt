package com.poptato.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray90
import com.poptato.design_system.Gray95
import com.poptato.design_system.PoptatoTypo

@Composable
fun OneBtnTypeDialog(
    onDismiss: () -> Unit = {},
) {

    OneBtnTypeDialogContent(
        onDismiss = onDismiss
    )
}

@Composable
fun OneBtnTypeDialogContent(
    onDismiss: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .width(328.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Gray95
        ) {
            Column {
                Text(
                    text = "dialog 테스트",
                    style = PoptatoTypo.lgSemiBold,
                    color = Gray00,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .align(Alignment.CenterHorizontally)
                )

                OneBtnTypeDialogBtn()
            }
        }
    }
}

@Composable
fun OneBtnTypeDialogBtn() {
    Text(
        text = "btn 테스트",
        style = PoptatoTypo.mdSemiBold,
        textAlign = TextAlign.Center,
        color = Gray00,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Gray90)
            .padding(vertical = 16.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOneBtnTypeDialog() {
    OneBtnTypeDialogContent()
}