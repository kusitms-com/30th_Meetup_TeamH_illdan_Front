package com.poptato.backlog

import com.poptato.core.util.move
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BacklogViewModel @Inject constructor(

) : BaseViewModel<BacklogPageState>(
    BacklogPageState()
) {
    fun onValueChange(newValue: String) {
        updateState(
            uiState.value.copy(
                taskInput = newValue
            )
        )
    }

    fun createBacklog(content: String) {
        val newList = uiState.value.backlogList.toMutableList()
        newList.add(0, TodoItemModel(content = content, todoId = Random.nextLong()))
        updateState(
            uiState.value.copy(
                backlogList = newList
            )
        )
    }

    fun removeBacklogItem(item: TodoItemModel) {
        val newList = uiState.value.backlogList.filter { it != item }

        updateState(
            uiState.value.copy(
                backlogList = newList
            )
        )
    }

    fun moveItem(fromIndex: Int, toIndex: Int) {
        val currentList = uiState.value.backlogList.toMutableList()
        currentList.move(fromIndex, toIndex)
        updateList(currentList)
    }

    private fun updateList(updatedList: List<TodoItemModel>) {
        updateState(
            uiState.value.copy(
                backlogList = updatedList
            )
        )
    }
}