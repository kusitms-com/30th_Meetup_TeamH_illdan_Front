package com.poptato.backlog

import androidx.lifecycle.viewModelScope
import com.poptato.core.enums.TodoType
import com.poptato.core.util.TimeFormatter
import com.poptato.core.util.move
import com.poptato.domain.model.request.ListRequestModel
import com.poptato.domain.model.request.backlog.CreateBacklogRequestModel
import com.poptato.domain.model.request.backlog.GetBacklogListRequestModel
import com.poptato.domain.model.request.todo.TodoIdModel
import com.poptato.domain.model.request.todo.DeadlineContentModel
import com.poptato.domain.model.request.todo.DragDropRequestModel
import com.poptato.domain.model.request.todo.ModifyTodoRequestModel
import com.poptato.domain.model.request.todo.UpdateDeadlineRequestModel
import com.poptato.domain.model.response.backlog.BacklogListModel
import com.poptato.domain.model.response.category.CategoryIconItemModel
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.domain.model.response.yesterday.YesterdayListModel
import com.poptato.domain.usecase.backlog.CreateBacklogUseCase
import com.poptato.domain.usecase.backlog.GetBacklogListUseCase
import com.poptato.domain.usecase.yesterday.GetYesterdayListUseCase
import com.poptato.domain.usecase.todo.DeleteTodoUseCase
import com.poptato.domain.usecase.todo.DragDropUseCase
import com.poptato.domain.usecase.todo.ModifyTodoUseCase
import com.poptato.domain.usecase.todo.SwipeTodoUseCase
import com.poptato.domain.usecase.todo.UpdateBookmarkUseCase
import com.poptato.domain.usecase.todo.UpdateDeadlineUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BacklogViewModel @Inject constructor(
    private val createBacklogUseCase: CreateBacklogUseCase,
    private val getBacklogListUseCase: GetBacklogListUseCase,
    private val getYesterdayListUseCase: GetYesterdayListUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val modifyTodoUseCase: ModifyTodoUseCase,
    private val dragDropUseCase: DragDropUseCase,
    private val updateDeadlineUseCase: UpdateDeadlineUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    private val swipeTodoUseCase: SwipeTodoUseCase
) : BaseViewModel<BacklogPageState>(
    BacklogPageState()
) {
    private var snapshotList: List<TodoItemModel> = emptyList()
    private var tempTodoId: Long? = null

    private val _categoryList: List<CategoryIconItemModel> = listOf(
        CategoryIconItemModel(11, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
        CategoryIconItemModel(12, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
        CategoryIconItemModel(13, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
    )

    init {
        getCategoryList()
        getYesterdayList(0, 1)
        getBacklogList(0, 100)
    }

    private fun getCategoryList() {
        // TODO 카테고리 리스트 서버통신 연결
        updateState(
            uiState.value.copy(
                categoryList = _categoryList
            )
        )
    }

    fun getBacklogListInCategory(categoryId: Long) {
        // TODO 선택한 카테고리에 대한 백로그 리스트 가져오는 것으로 수정
        updateState(
            uiState.value.copy(
                selectedCategoryId = categoryId
            )
        )
        Timber.d("[카테고리] 선택 -> $categoryId")
    }

    private fun getBacklogList(page: Int, size: Int) {
        viewModelScope.launch {
            getBacklogListUseCase.invoke(request = GetBacklogListRequestModel(page = page, size = size)).collect {
                resultResponse(it, ::onSuccessGetBacklogList)
            }
        }
    }

    private fun onSuccessGetBacklogList(response: BacklogListModel) {
        updateSnapshotList(response.backlogs)

        updateState(
            uiState.value.copy(
                backlogList = response.backlogs,
                totalPageCount = response.totalPageCount,
                totalItemCount = response.totalCount,
                isFinishedInitialization = true
            )
        )
    }

    private fun getYesterdayList(page: Int, size: Int) {
        viewModelScope.launch {
            getYesterdayListUseCase(request = ListRequestModel(page = page, size = size)).collect {
                resultResponse(it, { data ->
                    checkYesterdayListEmpty(data)
                    Timber.d("[어제 한 일] 서버통신 성공(Backlog) -> $data")
                }, { error ->
                    Timber.d("[어제 한 일] 서버통신 실패(Backlog) -> $error")
                })
            }
        }
    }

    private fun checkYesterdayListEmpty(response: YesterdayListModel) {
        updateState(
            uiState.value.copy(
                isYesterdayListEmpty = response.yesterdays.isEmpty()
            )
        )
    }

    fun onValueChange(newValue: String) {
        updateState(
            uiState.value.copy(
                taskInput = newValue
            )
        )
    }

    fun createBacklog(content: String) {
        addTemporaryBacklog(content)

        viewModelScope.launch(Dispatchers.IO) {
            createBacklogUseCase.invoke(request = CreateBacklogRequestModel(content)).collect {
                resultResponse(it, ::onSuccessCreateBacklog, { onFailedUpdateBacklogList() })
            }
        }
    }

    private fun addTemporaryBacklog(content: String) {
        val newList = uiState.value.backlogList.toMutableList()
        val temporaryId = Random.nextLong()

        newList.add(0, TodoItemModel(content = content, todoId = temporaryId))
        updateNewItemFlag(true)
        updateList(newList)
        tempTodoId = temporaryId
    }

    private fun onSuccessCreateBacklog(response: TodoIdModel) {
        val updatedList = uiState.value.backlogList.map { item ->
            if (item.todoId == tempTodoId) {
                item.copy(todoId = response.todoId)
            } else item
        }
        updateList(updatedList)

        getBacklogList(page = 0, size = uiState.value.backlogList.size)
    }

    private fun onFailedUpdateBacklogList() {
        updateList(snapshotList)
        emitEventFlow(BacklogEvent.OnFailedUpdateBacklogList)
    }

    fun swipeBacklogItem(item: TodoItemModel) {
        val newList = uiState.value.backlogList.filter { it.todoId != item.todoId }

        updateList(newList)

        viewModelScope.launch {
            swipeTodoUseCase.invoke(TodoIdModel(item.todoId)).collect {
                resultResponse(it, { updateSnapshotList(uiState.value.backlogList) }, { onFailedUpdateBacklogList() })
            }
        }
    }

    fun onMove(from: Int, to: Int) {
        val currentList = uiState.value.backlogList.toMutableList()
        currentList.move(from, to)
        updateList(currentList)
    }

    fun onDragEnd() {
        val todoIdList = uiState.value.backlogList.map { it.todoId }

        viewModelScope.launch {
            dragDropUseCase.invoke(
                request = DragDropRequestModel(
                    type = TodoType.BACKLOG,
                    todoIds = todoIdList
                )
            ).collect {
                resultResponse(it, { updateSnapshotList(uiState.value.backlogList) }, { onFailedUpdateBacklogList() })
            }
        }
    }

    private fun updateList(updatedList: List<TodoItemModel>) {
        val newList = updatedList.toList()
        updateState(
            uiState.value.copy(
                backlogList = newList
            )
        )
    }

    fun setDeadline(deadline: String?, id: Long) {
        updateDeadlineInUI(deadline = deadline, id = id)

        viewModelScope.launch {
            updateDeadlineUseCase.invoke(
                request = UpdateDeadlineRequestModel(
                    todoId = uiState.value.selectedItem.todoId,
                    deadline = DeadlineContentModel(deadline)
                )
            ).collect {
                resultResponse(it, { updateSnapshotList(uiState.value.backlogList) }, { onFailedUpdateBacklogList() })
            }
        }
    }

    private fun updateDeadlineInUI(deadline: String?, id: Long) {
        val dDay = TimeFormatter.calculateDDay(deadline)
        val newList = uiState.value.backlogList.map {
            if (it.todoId == id) {
                it.copy(deadline = deadline ?: "", dDay = dDay)
            } else {
                it
            }
        }
        val updatedItem = uiState.value.selectedItem.copy(deadline = deadline ?: "", dDay = dDay)

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

    fun deleteBacklog(id: Long) {
        val newList = uiState.value.backlogList.filter { it.todoId != id }
        updateList(newList)

        viewModelScope.launch {
            deleteTodoUseCase.invoke(id).collect {
                resultResponse(
                    it,
                    {
                        updateSnapshotList(uiState.value.backlogList)
                        emitEventFlow(BacklogEvent.OnSuccessDeleteBacklog)
                    },
                    { onFailedUpdateBacklogList() }
                )
            }
        }
    }

    fun modifyTodo(item: ModifyTodoRequestModel) {
        modifyTodoInUI(item = item)

        viewModelScope.launch {
            modifyTodoUseCase.invoke(
                request = item
            ).collect {
                resultResponse(it, { updateSnapshotList(uiState.value.backlogList) }, { onFailedUpdateBacklogList() })
            }
        }
    }

    private fun modifyTodoInUI(item: ModifyTodoRequestModel) {
        val newList = uiState.value.backlogList.map {
            if (it.todoId == item.todoId) {
                it.copy(content = item.content.content)
            } else {
                it
            }
        }

        updateList(newList)
    }

    fun updateNewItemFlag(flag: Boolean) {
        updateState(
            uiState.value.copy(
                isNewItemCreated = flag
            )
        )
    }

    fun updateBookmark(id: Long) {
        updateBookmarkInUI(id)

        viewModelScope.launch {
            updateBookmarkUseCase.invoke(id).collect {
                resultResponse(it, { updateSnapshotList(uiState.value.backlogList) }, { onFailedUpdateBacklogList() })
            }
        }
    }

    private fun updateBookmarkInUI(id: Long) {
        val newList = uiState.value.backlogList.map {
            if (it.todoId == id) {
                it.copy(isBookmark = !it.isBookmark)
            } else {
                it
            }
        }
        val updatedItem = uiState.value.selectedItem.copy(isBookmark = !uiState.value.selectedItem.isBookmark)

        updateState(
            uiState.value.copy(
                backlogList = newList,
                selectedItem = updatedItem
            )
        )
    }

    private fun updateSnapshotList(newList: List<TodoItemModel>) {
        snapshotList = newList
    }
}