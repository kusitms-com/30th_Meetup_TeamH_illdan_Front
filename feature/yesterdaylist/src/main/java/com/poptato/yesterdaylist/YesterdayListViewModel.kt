package com.poptato.yesterdaylist

import com.poptato.domain.model.enums.TodoStatus
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class YesterdayListViewModel @Inject constructor(

): BaseViewModel<YesterdayListPageState>(
    YesterdayListPageState()
) {

    private val _yesterdayList: List<TodoItemModel> = listOf(
        TodoItemModel(1, "test1", TodoStatus.INCOMPLETE, false),
        TodoItemModel(2, "test2", TodoStatus.INCOMPLETE, false),
        TodoItemModel(3, "test3", TodoStatus.INCOMPLETE, false),
        TodoItemModel(4, "test4", TodoStatus.INCOMPLETE, false),
        TodoItemModel(5, "test5", TodoStatus.INCOMPLETE, false),
        TodoItemModel(6, "test6", TodoStatus.INCOMPLETE, false),
        TodoItemModel(7, "test7", TodoStatus.INCOMPLETE, false),
        TodoItemModel(8, "test8", TodoStatus.INCOMPLETE, false),
        TodoItemModel(9, "test9", TodoStatus.INCOMPLETE, false),
        TodoItemModel(10, "test10", TodoStatus.INCOMPLETE, false),
        TodoItemModel(11, "test11", TodoStatus.INCOMPLETE, false),
        TodoItemModel(12, "test12", TodoStatus.INCOMPLETE, false),
        TodoItemModel(13, "test13", TodoStatus.INCOMPLETE, false),
        TodoItemModel(14, "test14", TodoStatus.INCOMPLETE, false),
        TodoItemModel(15, "test15", TodoStatus.INCOMPLETE, false),
    )

    init {
        getYesterdayList(_yesterdayList)    // TODO 서버통신 연결
    }

    private fun getYesterdayList(updatedList: List<TodoItemModel>) {
        updateState(
            uiState.value.copy(yesterdayList =  updatedList)
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
    }
}