package com.poptato.setting.userdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poptato.design_system.Danger40
import com.poptato.design_system.Danger50
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray40
import com.poptato.design_system.Gray70
import com.poptato.design_system.LogOut
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.ProfileDetail
import com.poptato.design_system.R
import com.poptato.design_system.UserDataEmail
import com.poptato.design_system.UserDataName
import com.poptato.design_system.UserDelete
import com.poptato.setting.logout.LogOutDialog
import com.poptato.setting.logout.LogOutDialogState

@Composable
fun UserDataScreen(
    goBackToMyPage: () -> Unit = {},
    goBackToLogIn: () -> Unit = {}
) {

    val viewModel: UserDataViewModel = hiltViewModel()
    val uiState: UserDataPageState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
    val logOutDialogState = viewModel.logOutDialogState.value

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UserDataEvent.GoBackToLogIn -> {
                    goBackToLogIn()
                }
            }
        }
    }

    EditUserDataContent(
        uiState = uiState,
        logOutDialogState = logOutDialogState,
        onClickBackBtn = { goBackToMyPage() },
        onClickLogOutBtn = { viewModel.showLogOutDialog() },
        interactionSource = interactionSource
    )
}

@Composable
fun EditUserDataContent(
    uiState: UserDataPageState = UserDataPageState(),
    logOutDialogState: LogOutDialogState,
    onClickBackBtn: () -> Unit = {},
    onClickLogOutBtn: () -> Unit = {},
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {

        SettingTitle(
            onClickBackBtn = onClickBackBtn,
            interactionSource = interactionSource
        )

        MyData(
            uiState = uiState
        )

        LogOutBtn(
            onClickLogOutBtn = onClickLogOutBtn
        )

        UserDataDetailContent(
            UserDataName, "내용"
        )
        UserDataDetailContent(
            UserDataEmail, "내용",
            topPadding = 24
        )

        UserDelete(
            interactionSource = interactionSource
        )
    }

    if (logOutDialogState.isShowDialog) {
        LogOutDialog(
            onDismiss = logOutDialogState.onDismissRequest,
            onClickBack = logOutDialogState.onClickBackBtn,
            onClickLogOut = logOutDialogState.onClickLogOutBtn
        )
    }
}

@Composable
fun SettingTitle(
    onClickBackBtn: () -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(width = 24.dp, height = 24.dp)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    onClick = { onClickBackBtn() }
                )
        )

        Text(
            text = ProfileDetail,
            style = PoptatoTypo.mdMedium,
            color = Gray00,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun MyData(
    uiState: UserDataPageState = UserDataPageState()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "img_temp_person",
                modifier = Modifier.size(64.dp)
            )

            Column {
                Text(
                    text = "이름",
                    color = Gray00,
                    style = PoptatoTypo.lgSemiBold,
                    modifier = Modifier
                        .offset(x = 12.dp, y = 8.dp)
                )

                Text(
                    text = "메일",
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
fun LogOutBtn(
    onClickLogOutBtn: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Danger50.copy(alpha = 0.1f))
            .wrapContentHeight()
            .clickable { onClickLogOutBtn() }
    ) {
        Text(
            text = LogOut,
            style = PoptatoTypo.smSemiBold,
            color = Danger40,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 12.dp)
        )
    }
}

@Composable
fun UserDataDetailContent(
    title: String = "",
    content: String = "",
    topPadding: Int = 0
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .padding(top = topPadding.dp)
    ) {
        Text(
            text = title,
            color = Gray40,
            style = PoptatoTypo.smMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = content,
            color = Gray00,
            style = PoptatoTypo.mdMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 2.dp)
        )
    }
}

@Composable
fun UserDelete(
    onClickAction: () -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = { onClickAction() }
            )
    ) {
        Text(
            text = UserDelete,
            color = Gray70,
            style = PoptatoTypo.mdMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    EditUserDataContent(logOutDialogState = LogOutDialogState())
}