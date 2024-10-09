package com.poptato.today

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(

) : BaseViewModel<TodayPageState>(TodayPageState()) {

    fun onCheckedTodo(value: Boolean, id: Long) {
        val selectedItem = uiState.value.todayList.todays.find { it.todoId == id }?.copy(isComplete = value)
        val remainingItems = uiState.value.todayList.todays.filter { it.todoId != id }
        val newTodays = if (selectedItem != null) {
            val incompleteItems = remainingItems.filter { !it.isComplete }.toMutableList()
            val completeItems = remainingItems.filter { it.isComplete }.toMutableList()

            if (selectedItem.isComplete) {
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