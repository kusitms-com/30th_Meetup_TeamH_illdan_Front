package com.potato.history

import com.poptato.ui.base.PageState
import com.potato.history.model.HistoryGroupedItem
import com.potato.history.model.HistoryItemModel

data class HistoryPageState(
    val searchText: String = "",
    val historyList: List<HistoryGroupedItem> = emptyList()
) : PageState