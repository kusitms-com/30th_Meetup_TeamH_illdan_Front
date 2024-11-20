package com.potato.history

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.request.backlog.GetBacklogListRequestModel
import com.poptato.domain.model.request.history.HistoryListRequestModel
import com.poptato.domain.model.response.backlog.BacklogListModel
import com.poptato.domain.model.response.history.HistoryItemModel
import com.poptato.domain.model.response.history.HistoryListModel
import com.poptato.domain.usecase.backlog.GetBacklogListUseCase
import com.poptato.domain.usecase.history.GetHistoryListUseCase
import com.poptato.ui.base.BaseViewModel
import com.potato.history.model.HistoryGroupedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import timber.log.Timber
import java.util.Collections.copy
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryListUseCase: GetHistoryListUseCase
) : BaseViewModel<HistoryPageState>(HistoryPageState()) {

    init {
        getHistoryList()
    }

    fun getHistoryList() {
        if (!uiState.value.isLoadingMore && (uiState.value.currentPage == 0 || uiState.value.currentPage < uiState.value.totalPageCount)) {
            updateState(
                uiState.value.copy(
                    isLoadingMore = true
                )
            )

            viewModelScope.launch {
                try {
                    getHistoryListUseCase.invoke(
                        request = HistoryListRequestModel(page = uiState.value.currentPage, size = uiState.value.pageSize)
                    ).collect { result ->
                        resultResponse(result, ::onSuccessGetHistoryList)
                    }
                } catch (e: Exception) {
                    Timber.e("Failed to load next page: $e")
                    updateState(
                        uiState.value.copy(
                            isLoadingMore = false
                        )
                    )
                }
            }
        }
    }

    private fun onSuccessGetHistoryList(response: HistoryListModel) {
        val newItems = response.histories
        val groupedItems = newItems.groupBy { it.date }.map { (date, items) ->
            HistoryGroupedItem(date, items)
        }
        val currentGroupedItems = uiState.value.historyList.toMutableList()
        val lastExistingDate = currentGroupedItems.lastOrNull()?.date
        val firstNewDate = groupedItems.firstOrNull()?.date

        if (lastExistingDate != null && lastExistingDate == firstNewDate) {
            val existingGroup = currentGroupedItems.lastOrNull()
            val newGroup = groupedItems.firstOrNull()

            if (existingGroup != null && newGroup != null) {
                val mergedItems = existingGroup.items + newGroup.items
                val updatedGroup = existingGroup.copy(items = mergedItems)
                currentGroupedItems[currentGroupedItems.lastIndex] = updatedGroup
            }

            currentGroupedItems.addAll(groupedItems.drop(1))
        } else {
            currentGroupedItems.addAll(groupedItems)
        }
        updateState(
            uiState.value.copy(
                historyList = currentGroupedItems,
                isLoadingMore = false,
                totalPageCount = response.totalPageCount,
                currentPage = uiState.value.currentPage + 1
            )
        )
    }

    // 날짜비교로 스티커 렌더링
    fun getEventTypeForDate(date: LocalDate, hasEvent: Boolean): HistoryEvent {
        return when {
            hasEvent && date.isBefore(LocalDate.now()) -> HistoryEvent.HasEvent
            date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now()) -> HistoryEvent.FutureEvent
            else -> HistoryEvent.NoEvent
        }
    }

    // get 캘린더 조회 API

    // update selected date (default는 오늘 날짜) -> selected date로 기록 조회

    // update current month (캘린더 보여주는 달) -> get 캘린더 + selected date로 기록 조회

}
