package com.poptato.setting.servicedelete

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poptato.design_system.Danger50
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray40
import com.poptato.design_system.Gray95
import com.poptato.design_system.MissingFeature
import com.poptato.design_system.NotUsed
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.R
import com.poptato.design_system.TooComplex
import com.poptato.design_system.UserDeleteBtn
import com.poptato.design_system.UserDeleteContent
import com.poptato.design_system.UserDeleteTitle
import com.poptato.domain.model.enums.UserDeleteType
import com.poptato.ui.common.PoptatoCheckBox

@Composable
fun ServiceDeleteScreen(
    goBackToSetting: () -> Unit = {},
    goBackToLogIn: () -> Unit = {}
) {

    val viewModel: ServiceDeleteViewModel = hiltViewModel()
    val uiState: ServiceDeletePageState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ServiceDeleteEvent.GoBackToLogIn -> {
                    goBackToLogIn()
                }
            }
        }
    }

    ServiceDeleteContent(
        onClickCloseBtn = { goBackToSetting() },
        onClickDeleteBtn = { viewModel.userDelete() },
        isSelectedReasonsList = uiState.selectedReasonList,
        onSelectedReason = { viewModel.setSelectedReason(it) }
    )
}

@Composable
fun ServiceDeleteContent(
    onClickCloseBtn: () -> Unit = {},
    onClickDeleteBtn: () -> Unit = {},
    onSelectedReason: (UserDeleteType) -> Unit = {},
    isSelectedReasonsList: List<UserDeleteType> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {

        CloseBtn(
            onClickCloseBtn = onClickCloseBtn
        )

        Text(
            text = UserDeleteTitle,
            color = Gray00,
            style = PoptatoTypo.xxLSemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 25.dp)
        )

        Text(
            text = UserDeleteContent,
            color = Gray40,
            style = PoptatoTypo.mdRegular,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 25.dp)
        )

        DeleteReasonsContent(
            isSelectedReasonsList = isSelectedReasonsList,
            onSelectedReason = onSelectedReason
        )

        UserDeleteBtn(
            onClickDeleteBtn = onClickDeleteBtn
        )
    }
}

@Composable
fun CloseBtn(
    onClickCloseBtn: () -> Unit = {}
) {
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
                .clickable { onClickCloseBtn() }
        )
    }
}

@Composable
fun DeleteReasonsContent(
    onSelectedReason: (UserDeleteType) -> Unit = {},
    isSelectedReasonsList: List<UserDeleteType> = emptyList()
) {

    val reasonsList: List<UserDeleteType> = listOf(UserDeleteType.NOT_USED_OFTEN, UserDeleteType.MISSING_FEATURES, UserDeleteType.TOO_COMPLEX)
    val reasonsContextList: List<String> = listOf(NotUsed, MissingFeature, TooComplex)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 17.dp)
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(reasonsList, key = { _, item -> item }) { index, item ->
            DeleteReasonsItem(
                reason = reasonsContextList[index],
                isSelected = isSelectedReasonsList.contains(item),
                onClick = { onSelectedReason(item) }
            )
        }
    }
}

@Composable
fun DeleteReasonsItem(
    reason: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Gray95)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .padding(vertical = 18.dp),
        ) {
            PoptatoCheckBox(
                isChecked = isSelected,
                onCheckedChange = {}
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = reason,
                style = PoptatoTypo.smMedium,
                color = Gray00
            )
        }
    }
}

@Composable
fun UserDeleteBtn(
    onClickDeleteBtn: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(y = 175.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Danger50.copy(alpha = 0.05f),
                            Color.Transparent
                        ),
                        startY = 400f,
                        endY = 200f
                    ),
                    shape = RectangleShape
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .background(Danger50, shape = RoundedCornerShape(12.dp))
                .clickable { onClickDeleteBtn() }
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetting() {
    ServiceDeleteContent()
}