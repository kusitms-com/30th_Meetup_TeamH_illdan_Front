package com.poptato.backlog

import androidx.lifecycle.viewModelScope
import com.poptato.core.util.move
import com.poptato.domain.model.request.backlog.CreateBacklogRequestModel
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.domain.usecase.backlog.CreateBacklogUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BacklogViewModel @Inject constructor(
    private val createBacklogUseCase: CreateBacklogUseCase
) : BaseViewModel<BacklogPageState>(
    BacklogPageState()
) {
    private var snapshotList: List<TodoItemModel> = emptyList()

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

        viewModelScope.launch(Dispatchers.IO) {
            createBacklogUseCase.invoke(request = CreateBacklogRequestModel(content)).collect {
                resultResponse(it, { onSuccessCreateBacklog() }, { onFailedCreateBacklog() })
            }
        }
    }

    private fun onSuccessCreateBacklog() {
        snapshotList = uiState.value.backlogList
    }

    private fun onFailedCreateBacklog() {
        updateState(
            uiState.value.copy(
                backlogList = snapshotList
            )
        )

        emitEventFlow(BacklogEvent.OnFailedCreateBacklog)
    }

    fun removeBacklogItem(item: TodoItemModel) {
        val newList = uiState.value.backlogList.filter { it.todoId != item.todoId }

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

    fun setDeadline(deadline: String) {
        val updatedItem = uiState.value.selectedItem.copy(deadline = deadline)
        val newList = uiState.value.backlogList.map {
            if (it.todoId == updatedItem.todoId) updatedItem
            else it
        }

        updateState(
            uiState.value.copy(
                backlogList = newList,
                selectedItem = updatedItem
            )
        )
    }

    fun onSelectedItem(item: TodoItemModel) {
        updateState(
            uiState.value.copy(
                selectedItem = item
            )
        )
    }
}