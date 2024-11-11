package com.poptato.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poptato.design_system.CategoryAddTitle
import com.poptato.design_system.CategoryNameInputTitle
import com.poptato.design_system.Complete
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray70
import com.poptato.design_system.Gray90
import com.poptato.design_system.Gray95
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.R

@Composable
fun CategoryScreen(
    goBackToBacklog: () -> Unit = {}
) {

    val interactionSource = remember { MutableInteractionSource() }

    CategoryContent(
        interactionSource = interactionSource,
        onClickBackBtn = { goBackToBacklog() }
    )
}

@Composable
fun CategoryContent(
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onClickBackBtn: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        CategoryTitle(
            interactionSource = interactionSource,
            onClickBackBtn = onClickBackBtn
        )

        CategoryAddContent()
    }
}

@Composable
fun CategoryTitle(
    onClickBackBtn: () -> Unit = {},
    interactionSource: MutableInteractionSource
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "back",
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(top = 28.dp, bottom = 4.dp)
                .size(width = 24.dp, height = 24.dp)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    onClick = { onClickBackBtn() }
                )
        )

        Text(
            text = CategoryAddTitle,
            style = PoptatoTypo.mdSemiBold,
            color = Gray00,
            modifier = Modifier
                .padding(top = 28.dp, bottom = 4.dp)
                .align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterEnd)
        ) {
            AddFinishBtn()
        }
    }
}

@Composable
fun AddFinishBtn() {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .background(Gray95)
    ) {
        Text(
            text = Complete,
            style = PoptatoTypo.smSemiBold,
            color = Gray00,
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 12.dp)
        )
    }
}

@Composable
fun CategoryAddContent(

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp)
        ) {
            CategoryNameTextField()
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_add_category_icon),
            contentDescription = "add category icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun CategoryNameTextField() {
    Box(
        modifier = Modifier
            .wrapContentHeight()
    ) {
        BasicTextField(
            value = "test",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            decorationBox = {
                Column {
                    Text(
                        text = CategoryNameInputTitle,
                        style = PoptatoTypo.xLMedium,
                        color = Gray70
                    )
                    Divider(
                        color = Gray90,
                        thickness = 1.dp,
                        modifier = Modifier.
                            padding(top = 4.dp)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategory() {
    CategoryContent()
}