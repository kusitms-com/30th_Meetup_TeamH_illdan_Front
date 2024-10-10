package com.poptato.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poptato.core.enums.DateType
import com.poptato.core.util.TimeFormatter
import com.poptato.design_system.Complete
import com.poptato.design_system.Confirm
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.Primary60
import com.poptato.ui.util.fadingEdge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    onDateSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onDismissRequest: () -> Unit = {}
) {
    val todayDate = TimeFormatter.getTodayYearMonthDay()
    val initialYear = todayDate[0].toInt()
    val initialMonth = todayDate[1].toInt()
    val initialDay = todayDate[2].toInt()
    val items = TimeFormatter.getDaysInMonth(initialYear, initialMonth)
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    DatePickerBottomSheetContent(
        initialYear = initialYear,
        initialMonth = initialMonth,
        initialDay = initialDay,
        items  = items,
        sheetState = sheetState,
        scope = coroutineScope,
        onDismissRequest = onDismissRequest,
        onDateSelected = onDateSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheetContent(
    initialYear: Int = -1,
    initialMonth: Int = -1,
    initialDay: Int = -1,
    items: List<Int> = emptyList(),
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    onDismissRequest: () -> Unit = {},
    onDateSelected: (Int, Int, Int) -> Unit = { _, _, _ -> }
) {
    val yearState = rememberLazyListState(initialFirstVisibleItemIndex = initialYear - 2000)
    val monthState = rememberLazyListState(initialFirstVisibleItemIndex = initialMonth - 1)
    val dayState = rememberLazyListState(initialFirstVisibleItemIndex = initialDay - 1)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch { sheetState.hide() }
            onDismissRequest()
        },
        containerColor = Gray100,
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    if (dragAmount.y > 0) {
                        return@detectDragGestures
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                DialPicker(
                    items = (2000..2026).toList(),
                    listState = yearState,
                    dateType = DateType.YEAR
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                DialPicker(
                    items = (1..12).toList(),
                    listState = monthState,
                    dateType = DateType.MONTH
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                DialPicker(
                    items = items,
                    listState = dayState,
                    dateType = DateType.DAY
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    val selectedYear = 2000 + yearState.firstVisibleItemIndex
                    val selectedMonth = 1 + monthState.firstVisibleItemIndex
                    val selectedDay = 1 + dayState.firstVisibleItemIndex
                    onDateSelected(selectedYear, selectedMonth, selectedDay)
                    onDismissRequest()
                }
                .clip(RoundedCornerShape(12.dp))
                .background(Primary60),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = Complete,
                style = PoptatoTypo.lgSemiBold,
                color = Gray100
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun DialPicker(
    items: List<Int>,
    listState: LazyListState,
    dateType: DateType = DateType.YEAR
) {
    val extendedItems = listOf(0, 0) + items + listOf(0, 0)
    val visibleItemsCount = 5
    val itemHeight = 30.dp
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .height(itemHeight * visibleItemsCount)
            .fadingEdge(fadingEdgeGradient),
        flingBehavior = flingBehavior
    ) {
        items(extendedItems.size) { index ->
            val item = extendedItems[index]
            val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
            val fontStyle = when (index) {
                firstVisibleItemIndex + 2 -> PoptatoTypo.xLSemiBold
                firstVisibleItemIndex + 1, firstVisibleItemIndex + 3 -> PoptatoTypo.lgSemiBold
                firstVisibleItemIndex, firstVisibleItemIndex + 4 -> PoptatoTypo.mdSemiBold
                else -> PoptatoTypo.smSemiBold
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight),
                contentAlignment = Alignment.Center
            ) {
                if (item != 0) {
                    Text(
                        text = "$item ${dateType.value}",
                        style = fontStyle,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Gray00
                    )
                }
            }
        }
    }
}