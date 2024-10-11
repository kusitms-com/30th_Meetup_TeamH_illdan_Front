package com.potato.history

import androidx.lifecycle.viewModelScope
import com.poptato.ui.base.BaseViewModel
import com.potato.history.model.HistoryGroupedItem
import com.potato.history.model.HistoryItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(

) : BaseViewModel<HistoryPageState>(
    HistoryPageState()
) {
    init {
        loadDummyHistoryItems()
    }

    private fun loadDummyHistoryItems() {
        val dummyItems =  // emptyList<HistoryItemModel>()
            listOf(
            HistoryItemModel(id = 1, title = "메일보내기", date = "2024.09.30", isChecked = true),
            HistoryItemModel(id = 2, title = "메일보내기", date = "2024.09.30", isChecked = true),
            HistoryItemModel(id = 3, title = "문서 검토하기", date = "2024.09.29", isChecked = true),
            HistoryItemModel(id = 4, title = "프로젝트 계획 작성", date = "2024.09.29", isChecked = true),
            HistoryItemModel(id = 5, title = "팀 미팅", date = "2024.09.28", isChecked = true)
        )

        val groupedItems = dummyItems.groupBy { it.date }.map { (date, items) ->
            HistoryGroupedItem(date, items)
        }

        updateState(uiState.value.copy(historyList = groupedItems))
    }

    fun onSearchTextChanged(newText: String) {
        updateState(
            uiState.value.copy(
                searchText = newText
            )
        )
    }

    fun onSearchTextClear() {
        updateState(
            uiState.value.copy(
                searchText = ""
            )
        )
    }

    fun onCalendarClick() {

    }

}