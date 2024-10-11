package com.poptato.setting.servicedelete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poptato.design_system.Danger50
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.R
import com.poptato.design_system.UserDeleteBtn
import com.poptato.design_system.UserDeleteTitle

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

        CloseBtn()

        Text(
            text = UserDeleteTitle,
            color = Gray00,
            style = PoptatoTypo.xxLSemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            //
        }

        UserDeleteBtn()
    }
}

@Composable
fun CloseBtn() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(end = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close_no_bg),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun UserDeleteBtn() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
            .background(Danger50, shape = RoundedCornerShape(12.dp))
    ) {
        Text(
            text = UserDeleteBtn,
            style = PoptatoTypo.lgSemiBold,
            color = Gray100,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    ServiceDeleteContent()
}