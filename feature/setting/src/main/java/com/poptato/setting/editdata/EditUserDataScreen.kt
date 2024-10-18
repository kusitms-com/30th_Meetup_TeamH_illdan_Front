package com.poptato.setting.editdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray95
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.ProfileDetail
import com.poptato.design_system.R
import com.poptato.design_system.UserNameTitle

@Composable
fun EditUserDataScreen(
    goBackToSetting: () -> Unit = {},
) {

    val viewModel: EditUserDataViewModel = hiltViewModel()
    val uiState: EditUserDataPageState by viewModel.uiState.collectAsStateWithLifecycle()

    EditUserDataContent(
        uiState = uiState,
        onClickCloseBtn = { goBackToSetting()  },
        onValueChange = { newValue -> viewModel.onValueChange(newValue) }
    )
}

@Composable
fun EditUserDataContent(
    uiState: EditUserDataPageState = EditUserDataPageState(),
    onClickCloseBtn: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {

        SettingTitle(
            onClickCloseBtn = onClickCloseBtn
        )

        UserImg()

        UserName(
            textInput = uiState.textInput,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun SettingTitle(
    onClickCloseBtn: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = ProfileDetail,
            style = PoptatoTypo.mdMedium,
            color = Gray00,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Center)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_close_no_bg),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(width = 24.dp, height = 24.dp)
                .clickable { onClickCloseBtn() }
        )
    }
}

@Composable
fun UserImg() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "img_temp_person",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun UserName(
    textInput: String,
    onValueChange: (String) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = UserNameTitle,
            color = Gray00,
            style = PoptatoTypo.mdSemiBold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Gray95)
        ) {

            BasicTextField(
                value = textInput,
                onValueChange = { newValue ->
                    onValueChange(newValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                textStyle = PoptatoTypo.smMedium.copy(
                    color = Gray00
                ),
                cursorBrush = SolidColor(Gray00),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    EditUserDataContent()
}