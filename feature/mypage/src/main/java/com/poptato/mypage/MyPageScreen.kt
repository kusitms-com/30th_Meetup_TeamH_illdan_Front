package com.poptato.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray20
import com.poptato.design_system.Gray40
import com.poptato.design_system.Gray95
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.Primary60
import com.poptato.design_system.ProfileDetail
import com.poptato.design_system.R

@Composable
fun MyPageScreen(
    goToSettingPage: () -> Unit = {}
) {

    val viewModel: MyPageViewModel = hiltViewModel()
    val uiState: MyPagePageState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageContent(
        uiState = uiState,
        onClickSettingBtn = { goToSettingPage() }
    )
}

@Composable
fun MyPageContent(
    uiState: MyPagePageState = MyPagePageState(),
    onClickSettingBtn: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {

        SettingBtn(
            onClickSettingBtn = onClickSettingBtn
        )

        MyData(
            uiState = uiState
        )

        UserDataBtn()

    }
}

@Composable
fun SettingBtn(
    onClickSettingBtn: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(end = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_setting),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopEnd)
                .clickable { onClickSettingBtn() }
        )
    }
}

@Composable
fun MyData(
    uiState: MyPagePageState = MyPagePageState()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "img_temp_person",
                modifier = Modifier.size(64.dp)
            )

            Column {
                Text(
                    text = uiState.userDataModel.name,
                    color = Gray00,
                    style = PoptatoTypo.lgSemiBold,
                    modifier = Modifier
                        .offset(x = 12.dp, y = 8.dp)
                )

                Text(
                    text = uiState.userDataModel.email,
                    color = Gray40,
                    style = PoptatoTypo.smRegular,
                    modifier = Modifier
                        .offset(x = 12.dp, y = 10.dp)
                )
            }
        }
    }
}

@Composable
fun UserDataBtn() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gray95)
            .wrapContentHeight()
    ) {
        Text(
            text = ProfileDetail,
            style = PoptatoTypo.smSemiBold,
            color = Gray00,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 11.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    MyPageContent()
}