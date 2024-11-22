package com.poptato.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poptato.design_system.DEADLINE_OPTION
import com.poptato.design_system.Danger50
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray30
import com.poptato.design_system.Gray60
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.R
import com.poptato.design_system.REPEAT_TASK_OPTION
import com.poptato.design_system.DELETE_ACTION
import com.poptato.design_system.Gray95
import com.poptato.design_system.modify
import com.poptato.domain.model.response.today.TodoItemModel

@Composable
fun TodoBottomSheet(
    item: TodoItemModel = TodoItemModel(),
    onClickShowDatePicker: () -> Unit = {},
    onClickBtnDelete: (Long) -> Unit = {},
    onClickBtnModify: (Long) -> Unit = {},
    onClickBtnBookmark: (Long) -> Unit = {},
    onClickBtnRepeat: (Long) -> Unit = {}
) {
    var deadline by remember { mutableStateOf(item.deadline) }
    var isBookmark by remember { mutableStateOf(item.isBookmark) }

    LaunchedEffect(item) {
        deadline = item.deadline
        isBookmark = item.isBookmark
    }

    TodoBottomSheetContent(
        item = item.copy(deadline = deadline, isBookmark = isBookmark),
        onClickShowDatePicker = onClickShowDatePicker,
        onClickBtnDelete = onClickBtnDelete,
        onClickBtnModify = onClickBtnModify,
        onClickBtnBookmark = {
            onClickBtnBookmark(it)
            isBookmark = !isBookmark
        },
        onClickBtnRepeat = onClickBtnRepeat
    )
}

@Composable
fun TodoBottomSheetContent(
    item: TodoItemModel = TodoItemModel(),
    onClickShowDatePicker: () -> Unit = {},
    onClickBtnDelete: (Long) -> Unit = {},
    onClickBtnModify: (Long) -> Unit = {},
    onClickBtnBookmark: (Long) -> Unit = {},
    onClickBtnRepeat: (Long) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(324.dp)
            .background(Gray95)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray95)
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
        ) {

            Text(
                text = item.content,
                style = PoptatoTypo.lgMedium,
                color = Gray00,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            if (item.isBookmark) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_filled),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.clickable { onClickBtnBookmark(item.todoId) }
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_empty),
                    contentDescription = "",
                    tint = Gray60,
                    modifier = Modifier.clickable { onClickBtnBookmark(item.todoId) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BottomSheetBtn(resourceId = R.drawable.ic_pen, buttonText = modify, textColor = Gray30, modifier = Modifier.clickable {
            onClickBtnModify(item.todoId)
        })
        BottomSheetBtn(resourceId = R.drawable.ic_trash, buttonText = DELETE_ACTION, textColor = Danger50, modifier = Modifier.clickable {
            onClickBtnDelete(item.todoId)
        })
        BottomSheetBtn(
            resourceId = R.drawable.ic_refresh,
            buttonText = REPEAT_TASK_OPTION,
            textColor = Gray30,
            modifier = Modifier.clickable {
                onClickBtnRepeat(item.todoId)
            },
            isRepeatBtn = true,
            isRepeat = item.isRepeat,
            onClickBtnRepeat = { onClickBtnRepeat(item.todoId) }
        )
        BottomSheetBtn(
            resourceId = R.drawable.ic_calendar,
            buttonText = DEADLINE_OPTION,
            textColor = Gray30,
            modifier = Modifier.clickable {
                onClickShowDatePicker()
            },
            deadline = item.deadline
        )
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun BottomSheetBtn(
    resourceId: Int,
    resourceColor: Color = Color.Unspecified,
    buttonText: String,
    textColor: Color,
    deadline: String = "",
    modifier: Modifier = Modifier,
    isRepeatBtn: Boolean = false,
    isRepeat: Boolean = false,
    onClickBtnRepeat: () -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(isRepeat) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        Icon(painter = painterResource(id = resourceId), contentDescription = null, tint = resourceColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = buttonText, style = PoptatoTypo.mdRegular, color = textColor)
        Spacer(modifier = Modifier.weight(1f))
        if (deadline.isNotEmpty()) Text(text = deadline, style = PoptatoTypo.mdMedium, color = Gray00)
        if (isRepeatBtn) PoptatoSwitchButton(
            check = isChecked,
            onClick = {
                isChecked = !isChecked
                onClickBtnRepeat()
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBottomSheet() {
    TodoBottomSheetContent()
}