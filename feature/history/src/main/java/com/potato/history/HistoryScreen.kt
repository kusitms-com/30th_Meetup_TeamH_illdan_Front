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
import com.poptato.design_system.HistoryTitle
import com.poptato.design_system.PoptatoTypo
import com.poptato.design_system.R
import com.poptato.domain.model.response.history.HistoryItemModel


@Composable
fun HistoryScreen(

) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val uiState: HistoryPageState by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryContent(
        uiState = uiState
    )
}

@Composable
fun HistoryContent(
    uiState: HistoryPageState = HistoryPageState()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
    ) {
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
            Text(
                text = HistoryTitle,
                color = Gray00,
                style = PoptatoTypo.xLSemiBold,
                modifier = Modifier
                    .padding(start = 16.dp, top = 13.dp, bottom = 13.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = (0).dp, y = (-16).dp)
            ) {
                items(uiState.historyList,
                    key = { groupedItem -> groupedItem.date }) { groupedItem ->
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
fun DateHeader(date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = date,
            color = Gray70,
            style = PoptatoTypo.xsRegular,
            modifier = Modifier
                .padding(end = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = Gray95
        )
    }
}

@Composable
fun HistoryListItem(item: HistoryItemModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
            .background(Color.Unspecified, shape = MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_history_checked),
            contentDescription = "Check",
            tint =  Color.Unspecified,
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(
            text = item.content,
            color = Gray00,
            style = PoptatoTypo.smMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHistory() {
    HistoryContent()
}