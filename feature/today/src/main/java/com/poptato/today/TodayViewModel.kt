package com.poptato.today

import com.poptato.domain.model.enums.TodoStatus
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(

) : BaseViewModel<TodayPageState>(TodayPageState()) {

    fun onCheckedTodo(status: TodoStatus, id: Long) {
        val newStatus = if(status == TodoStatus.COMPLETED) TodoStatus.INCOMPLETE else TodoStatus.COMPLETED
        val selectedItem = uiState.value.todayList.todays.find { it.todoId == id }?.copy(todoStatus = newStatus)
        val remainingItems = uiState.value.todayList.todays.filter { it.todoId != id }
        val newTodays = if (selectedItem != null) {
            val incompleteItems = remainingItems.filter { it.todoStatus == TodoStatus.INCOMPLETE }.toMutableList()
            val completeItems = remainingItems.filter { it.todoStatus == TodoStatus.COMPLETED }.toMutableList()

            if (selectedItem.todoStatus == TodoStatus.COMPLETED) {
                completeItems.add(selectedItem)
            } else {
                incompleteItems.add(selectedItem)
            }

            incompleteItems + completeItems
        } else {
            remainingItems
        }

        updateState(
            uiState.value.copy(
                todayList = uiState.value.todayList.copy(
                    todays = newTodays
                )
            )
        )
    }
}