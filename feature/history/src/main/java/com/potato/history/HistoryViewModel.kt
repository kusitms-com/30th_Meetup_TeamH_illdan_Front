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
import com.potato.history.model.MonthNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
                        request = HistoryListRequestModel(page = uiState.value.currentPage, size = uiState.value.pageSize, date = uiState.value.selectedDate)
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
        updateState(
            uiState.value.copy(
                historyList = newItems,
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
    fun updateSelectedDate(selectedDate: String) {
        updateState(
            uiState.value.copy(
                selectedDate = selectedDate,
                currentPage = 0,
                isLoadingMore = false,
            )
        )
        // 선택된 날짜 기준으로 기록 조회
        viewModelScope.launch {
            getHistoryList()
        }
        Timber.d("Selected date updated to: $selectedDate")
    }
    // update current month (캘린더 보여주는 달) -> get 캘린더 + selected date로 기록 조회
    fun updateCurrentMonth(dir: MonthNav) {
        val updatedMonthStartDate = when (dir) {
            MonthNav.PREVIOUS -> uiState.value.currentMonthStartDate.minusMonths(1).withDayOfMonth(1)
            MonthNav.NEXT -> uiState.value.currentMonthStartDate.plusMonths(1).withDayOfMonth(1)
        }

        updateState(
            uiState.value.copy(
                currentMonthStartDate = updatedMonthStartDate
            )
        )

        viewModelScope.launch {
            getHistoryList()
        }
        Timber.d("Current month updated to: $updatedMonthStartDate")
    }
}
