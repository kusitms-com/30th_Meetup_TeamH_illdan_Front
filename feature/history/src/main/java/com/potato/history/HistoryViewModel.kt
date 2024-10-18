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
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryListUseCase: GetHistoryListUseCase
) : BaseViewModel<HistoryPageState>(
    HistoryPageState()
) {
    init {
        getHistoryList(0, 15)
    }

    private fun getHistoryList(page: Int, size: Int) {
        viewModelScope.launch {
            getHistoryListUseCase.invoke(request = HistoryListRequestModel(page = page, size = size)).collect {
                resultResponse(it, ::onSuccessGetHistoryList)
            }
        }
    }

    private fun onSuccessGetHistoryList(response: HistoryListModel) {
        val dummyItems = response.histories

        val groupedItems = dummyItems.groupBy { it.date }.map { (date, items) ->
            HistoryGroupedItem(date, items)
        }

        updateState(uiState.value.copy(historyList = groupedItems))
    }

}