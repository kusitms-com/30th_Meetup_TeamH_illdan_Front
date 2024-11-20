package com.potato.history

import com.poptato.ui.base.PageState
import com.potato.history.model.HistoryGroupedItem
import com.potato.history.model.HistoryItemModel
import java.time.LocalDate

data class HistoryPageState(
    val historyList: List<HistoryGroupedItem> = emptyList(),
    val totalPageCount: Int = -1,
    val isLoadingMore: Boolean = false,
    var currentPage: Int = 0,
    val pageSize: Int = 15,
    val currentMonthStartDate: LocalDate = LocalDate.now().withDayOfMonth(1),
    val selectedDate: LocalDate = LocalDate.now(),
    val eventDates: List<LocalDate> = emptyList(),

) : PageState