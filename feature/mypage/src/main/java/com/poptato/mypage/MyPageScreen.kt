package com.poptato.mypage

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.barteksc.pdfviewer.PDFView
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.poptato.design_system.FAQ
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray20
import com.poptato.design_system.Gray40
import com.poptato.design_system.Gray95
import com.poptato.design_system.Notice
import com.poptato.design_system.Policy
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.Primary60
import com.poptato.design_system.ProfileDetail
import com.poptato.design_system.R
import com.poptato.design_system.SettingTitle
import com.poptato.design_system.Version
import com.poptato.design_system.VersionSetting
import com.poptato.mypage.BuildConfig.VERSION_NAME
import com.poptato.mypage.MyPageViewModel.Companion.FAQ_TYPE
import com.poptato.mypage.MyPageViewModel.Companion.NOTICE_TYPE
import com.poptato.mypage.MyPageViewModel.Companion.POLICY_TYPE
import timber.log.Timber
import java.io.File

@Composable
fun MyPageScreen(
    goToUserDataPage: () -> Unit = {},
    goToNoticeViewerPage: () -> Unit = {},
    goToFAQViewerPage: () -> Unit = {}
) {

    val viewModel: MyPageViewModel = hiltViewModel()
    val uiState: MyPagePageState by viewModel.uiState.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    MyPageContent(
        uiState = uiState,
        onClickUserDataBtn = { goToUserDataPage() },
        interactionSource = interactionSource,
        onClickServiceNotice = { viewModel.updateState(true, NOTICE_TYPE) },
        onClickServiceFAQ = { viewModel.updateState(true, FAQ_TYPE) },
        onClickPolicyBtn = { viewModel.updateState(true, POLICY_TYPE) },
    )

    if (uiState.noticeWebViewState) {
        goToNoticeViewerPage()
    }

    if (uiState.faqWebViewState) {
        goToFAQViewerPage()
    }

    if (uiState.policyViewState) {
        PolicyPDFView(
            pdfResId = R.raw.policy,
            onBack = { viewModel.updateState(false, POLICY_TYPE) }
        )
    }
}

@Composable
fun MyPageContent(
    uiState: MyPagePageState = MyPagePageState(),
    onClickUserDataBtn: () -> Unit = {},
    onClickServiceNotice: () -> Unit = {},
    onClickServiceFAQ: () -> Unit = {},
    onClickPolicyBtn: () -> Unit = {},
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {

        MyData(
            uiState = uiState
        )

        UserDataBtn(
            onClickUserDataBtn = onClickUserDataBtn
        )

        SettingSubTitle(
            title = SettingTitle,
            topPadding = 24
        )

        SettingServiceItem(
            title = Notice,
            interactionSource = interactionSource,
            onClickAction = { onClickServiceNotice() }
        )
        SettingServiceItem(
            title = FAQ,
            interactionSource = interactionSource,
            onClickAction = { onClickServiceFAQ() }
        )
        SettingServiceItem(
            title = Policy,
            interactionSource = interactionSource,
            onClickAction = { onClickPolicyBtn() }
        )
        SettingServiceItem(
            title = Version,
            isVersion = true,
            interactionSource = interactionSource
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
fun UserDataBtn(
    onClickUserDataBtn: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gray95)
            .wrapContentHeight()
            .clickable { onClickUserDataBtn() }
    ) {
        Text(
            text = ProfileDetail,
            style = PoptatoTypo.smSemiBold,
            color = Gray00,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 12.dp)
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
    color: Color = Gray20,
    isVersion: Boolean = false,
    onClickAction: () -> Unit = {},
    interactionSource: MutableInteractionSource
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = { onClickAction() }
            )
    ) {
        Text(
            text = title,
            color = color,
            style = PoptatoTypo.mdMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (isVersion) {
            Text(
                text = String.format(VersionSetting, VERSION_NAME),
                color = Primary60,
                style = PoptatoTypo.mdMedium,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp)
            )
        }
    }
}

@Composable
fun PolicyPDFView(
    pdfResId: Int,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val pdfFile = remember {
        val inputStream = context.resources.openRawResource(pdfResId)
        val outputFile = File(context.cacheDir, "policy_new.pdf")
        inputStream.use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        outputFile
    }

    BackHandler(enabled = true) {
        onBack()
    }

    AndroidView(
        factory = { ctx ->
            PDFView(ctx, null).apply {
                fromFile(pdfFile)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .load()
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
//    MyPageContent()
}