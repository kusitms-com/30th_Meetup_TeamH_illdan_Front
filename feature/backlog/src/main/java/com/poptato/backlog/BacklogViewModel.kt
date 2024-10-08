package com.poptato.backlog

import com.poptato.core.util.move
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.filterList
import javax.inject.Inject

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

    fun createBacklog(item: String) {
        val newList = uiState.value.backlogList.toMutableList()
        newList.add(0, item)
        updateState(
            uiState.value.copy(
                backlogList = newList
            )
        )
    }

    fun removeBacklogItem(item: String) {
        val newList = uiState.value.backlogList.filter { it != item }

        updateState(
            uiState.value.copy(
                backlogList = newList
            )
        )
    }

    fun moveBacklogItem(fromIndex: Int, toIndex: Int) {
        val updatedList = uiState.value.backlogList.toMutableList()
        val item = updatedList.removeAt(fromIndex)
        updatedList.add(toIndex, item)

        updateState(
            uiState.value.copy(
                backlogList = updatedList
            )
        )
    }

    fun moveItem(fromIndex: Int, toIndex: Int) {
        val currentList = uiState.value.backlogList.toMutableList()
        currentList.move(fromIndex, toIndex)
        updateList(currentList)
    }

    private fun updateList(updatedList: List<String>) {
        updateState(
            uiState.value.copy(
                backlogList = updatedList
            )
        )
    }
}