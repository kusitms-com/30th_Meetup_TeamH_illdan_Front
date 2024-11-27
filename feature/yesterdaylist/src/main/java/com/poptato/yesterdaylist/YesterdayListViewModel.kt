package com.poptato.yesterdaylist

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.enums.TodoStatus
import com.poptato.domain.model.request.ListRequestModel
import com.poptato.domain.model.response.yesterday.YesterdayListModel
import com.poptato.domain.usecase.todo.UpdateTodoCompletionUseCase
import com.poptato.domain.usecase.yesterday.GetYesterdayListUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class YesterdayListViewModel @Inject constructor(
    private val getYesterdayListUseCase: GetYesterdayListUseCase,
    private val updateTodoCompletionUseCase: UpdateTodoCompletionUseCase
): BaseViewModel<YesterdayListPageState>(
    YesterdayListPageState()
) {

    init {
        getYesterdayList(0, 8)
    }

    private fun getYesterdayList(page: Int, size: Int) {
        viewModelScope.launch {
            getYesterdayListUseCase(request = ListRequestModel(page = page, size = size)).collect {
                resultResponse(it, { data ->
                    setMappingToYesterdayList(data)
                    Timber.d("[어제 한 일] 서버통신 성공 -> $data")
                }, { error ->
                    Timber.d("[어제 한 일] 서버통신 실패 -> $error")
                })
            }
        }
    }

    private fun setMappingToYesterdayList(response: YesterdayListModel) {
        updateState(
            uiState.value.copy(
                yesterdayList = response.yesterdays,
                totalPageCount = response.totalPageCount
            )
        )
    }

    fun onCheckedTodo(id: Long, status: TodoStatus) {
        Timber.d("[어제 한 일] check -> id: $id & status: $status")

        val newStatus = if (status == TodoStatus.COMPLETED) TodoStatus.INCOMPLETE else TodoStatus.COMPLETED

        val updatedList = uiState.value.yesterdayList.map { item ->
            if (item.todoId == id) {
                item.copy(todoStatus = newStatus)
            } else {
                item
            }
        }

        updateState(
            uiState.value.copy(yesterdayList = updatedList)
        )

        updateTodoApi(id)
    }

    fun onCheckAllTodoList() {
        val yesterdayList = uiState.value.yesterdayList

        yesterdayList.forEach { item ->
            updateTodoApi(item.todoId)
        }
    }

    private fun updateTodoApi(id: Long) {
        viewModelScope.launch {
            updateTodoCompletionUseCase(id).collect {
                resultResponse(it, {
                    getYesterdayList(0, 8)
                }, { error ->
                    Timber.d("[어제 한 일] 달성 여부 수정 서버통신 실패 -> ${error.message}")
                })
            }
        }
    }
}