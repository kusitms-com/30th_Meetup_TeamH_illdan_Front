package com.poptato.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poptato.design_system.EditProfile
import com.poptato.design_system.Notice
import com.poptato.design_system.FAQ
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray20
import com.poptato.design_system.Gray60
import com.poptato.design_system.LogOut
import com.poptato.design_system.Policy
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.ProfileDetail
import com.poptato.design_system.ProfileTitle
import com.poptato.design_system.R
import com.poptato.design_system.ServiceTitle
import com.poptato.design_system.SettingTitle
import com.poptato.design_system.UserDelete
import com.poptato.design_system.Version

@Composable
fun SettingScreen() {

    val viewModel: SettingViewModel = hiltViewModel()
    val uiState: SettingPageState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingContent()
}

@Composable
fun SettingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {

        SettingTitle()

        SettingSubTitle(
            title = ProfileTitle
        )
        SettingServiceItem(
            title = EditProfile
        )
        SettingServiceItem(
            title = ProfileDetail
        )
        SettingServiceItem(
            title = LogOut
        )

        SettingSubTitle(
            title = ServiceTitle,
            topPadding = 40
        )
        SettingServiceItem(
            title = Notice
        )
        SettingServiceItem(
            title = FAQ
        )
        SettingServiceItem(
            title = Policy
        )
        SettingServiceItem(
            title = Version
        )
        SettingServiceItem(
            title = UserDelete,
            color = Gray60
        )
    }
}

@Composable
fun SettingTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = SettingTitle,
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
        )
    }
}

@Composable
fun SettingSubTitle(
    title: String,
    topPadding: Int = 0
) {
    Text(
        text = title,
        color = Gray00,
        style = PoptatoTypo.lgSemiBold,
        modifier = Modifier
            .padding(start = 16.dp, top = topPadding.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingServiceItem(
    title: String,
    color: Color = Gray20
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(start = 24.dp)
    ) {
        Text(
            text = title,
            color = color,
            style = PoptatoTypo.mdMedium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    SettingContent()
}