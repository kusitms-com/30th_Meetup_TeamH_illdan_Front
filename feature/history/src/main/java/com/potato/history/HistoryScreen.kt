package com.potato.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.poptato.design_system.Gray00
import com.poptato.design_system.Gray100
import com.poptato.design_system.Gray20
import com.poptato.design_system.Gray70
import com.poptato.design_system.Gray80
import com.poptato.design_system.Gray95
import com.poptato.design_system.HistoryListEmpty
import com.poptato.design_system.HistorySearchHint
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.R
import com.potato.history.model.HistoryItemModel


@Preview
@Composable
fun HistoryScreen(

) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val uiState: HistoryPageState by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryContent(
        uiState = uiState,
        onSearchTextChange = { viewModel.onSearchTextChanged(it) },
        onClearText = { viewModel.onSearchTextClear() },
        onCalendarClick = { viewModel.onCalendarClick() }
    )
}

@Composable
fun HistoryContent(
    uiState: HistoryPageState,
    onSearchTextChange: (String) -> Unit,
    onClearText: () -> Unit,
    onCalendarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
        TopSearchBar(
            searchText = uiState.searchText,
            onSearchTextChange = onSearchTextChange,
            onClearText = onClearText,
            onCalendarClick = onCalendarClick
        )

        if (uiState.historyList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = HistoryListEmpty,
                    color = Gray80,
                    style = PoptatoTypo.lgMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp)
                    .offset(x = (0).dp, y = (-16).dp)
            ) {
                items(uiState.historyList) { groupedItem ->
                    DateHeader(date = groupedItem.date)
                    groupedItem.items.forEach { item ->
                        HistoryListItem(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun TopSearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClearText: () -> Unit,
    onCalendarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 5.dp, top = 10.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .background(
                    color = if (searchText.isEmpty()) Gray100 else Gray95,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history_search),
                    contentDescription = "Search icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(end = 8.dp)
                )

                BasicTextField(
                    value = searchText,
                    onValueChange = onSearchTextChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    cursorBrush = SolidColor(Gray20),
                    decorationBox = { innerTextField ->
                        if (searchText.isEmpty()) {
                            Text(
                                text = HistorySearchHint,
                                color = Gray70
                            )
                        }
                        innerTextField()
                    }
                )

                if (searchText.isNotEmpty()) {
                    IconButton(onClick = onClearText, modifier = Modifier.size(24.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_history_delete),
                            contentDescription = "Clear text",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onCalendarClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_history_calendar),
                contentDescription = "Calendar",
                tint = Color.Unspecified
            )
        }
    }
}



@Composable
fun DateHeader(date: String) {

}

@Composable
fun HistoryListItem(item: HistoryItemModel) {

}