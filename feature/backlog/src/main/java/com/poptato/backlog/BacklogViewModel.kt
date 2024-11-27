package com.poptato.backlog

import androidx.lifecycle.viewModelScope
import com.poptato.core.enums.TodoType
import com.poptato.core.util.TimeFormatter
import com.poptato.core.util.move
import com.poptato.domain.model.request.ListRequestModel
import com.poptato.domain.model.request.backlog.CreateBacklogRequestModel
import com.poptato.domain.model.request.backlog.GetBacklogListRequestModel
import com.poptato.domain.model.request.category.GetCategoryListRequestModel
import com.poptato.domain.model.request.todo.DeadlineContentModel
import com.poptato.domain.model.request.todo.DragDropRequestModel
import com.poptato.domain.model.request.todo.ModifyTodoRequestModel
import com.poptato.domain.model.request.todo.TodoCategoryIdModel
import com.poptato.domain.model.request.todo.TodoIdModel
import com.poptato.domain.model.request.todo.UpdateDeadlineRequestModel
import com.poptato.domain.model.request.todo.UpdateTodoCategoryModel
import com.poptato.domain.model.response.backlog.BacklogListModel
import com.poptato.domain.model.response.category.CategoryListModel
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.domain.model.response.todo.TodoDetailItemModel
import com.poptato.domain.model.response.yesterday.YesterdayListModel
import com.poptato.domain.usecase.backlog.CreateBacklogUseCase
import com.poptato.domain.usecase.backlog.GetBacklogListUseCase
import com.poptato.domain.usecase.category.DeleteCategoryUseCase
import com.poptato.domain.usecase.category.GetCategoryListUseCase
import com.poptato.domain.usecase.todo.DeleteTodoUseCase
import com.poptato.domain.usecase.todo.DragDropUseCase
import com.poptato.domain.usecase.todo.GetTodoDetailUseCase
import com.poptato.domain.usecase.todo.ModifyTodoUseCase
import com.poptato.domain.usecase.todo.SwipeTodoUseCase
import com.poptato.domain.usecase.todo.UpdateBookmarkUseCase
import com.poptato.domain.usecase.todo.UpdateDeadlineUseCase
import com.poptato.domain.usecase.todo.UpdateTodoRepeatUseCase
import com.poptato.domain.usecase.todo.UpdateTodoCategoryUseCase
import com.poptato.domain.usecase.yesterday.GetYesterdayListUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BacklogViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val createBacklogUseCase: CreateBacklogUseCase,
    private val getBacklogListUseCase: GetBacklogListUseCase,
    private val getYesterdayListUseCase: GetYesterdayListUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val modifyTodoUseCase: ModifyTodoUseCase,
    private val dragDropUseCase: DragDropUseCase,
    private val updateDeadlineUseCase: UpdateDeadlineUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    private val updateTodoCategoryUseCase: UpdateTodoCategoryUseCase,
    private val getTodoDetailUseCase: GetTodoDetailUseCase,
    private val swipeTodoUseCase: SwipeTodoUseCase,
    private val updateTodoRepeatUseCase: UpdateTodoRepeatUseCase
) : BaseViewModel<BacklogPageState>(
    BacklogPageState()
) {
    private var snapshotList: List<TodoItemModel> = emptyList()
    private var tempTodoId: Long? = null

    init {
        getCategoryList()
        getYesterdayList(0, 1)
        getBacklogList(-1, 0, 100)
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            getCategoryListUseCase(request = GetCategoryListRequestModel(0, 8)).collect {
                resultResponse(it, { data ->
                    onSuccessGetCategoryList(data)
                })
            }
        }
    }

    private fun onSuccessGetCategoryList(response: CategoryListModel) {
        updateState(
            uiState.value.copy(
                categoryList = response.categoryList
            )
        )
    }

    fun deleteCategory() {
        viewModelScope.launch {
            deleteCategoryUseCase(request = uiState.value.selectedCategoryId).collect {
                resultResponse(it, {
                    getCategoryList()
                    updateState(uiState.value.copy(selectedCategoryId = -1, selectedCategoryIndex = 0))
                    getBacklogList(-1, 0, 100)
                }, { error ->
                    Timber.d("[카테고리] 삭제 서버통신 실패 -> $error")
                })
            }
        }
    }

    fun getBacklogListInCategory(categoryIndex: Int) {
        updateState(
            uiState.value.copy(
                selectedCategoryIndex = categoryIndex,
                selectedCategoryId = uiState.value.categoryList[categoryIndex].categoryId
            )
        )

        getBacklogList(uiState.value.selectedCategoryId, 0, 8)
    }

    private fun getBacklogList(categoryId: Long, page: Int, size: Int) {
        viewModelScope.launch {
            getBacklogListUseCase.invoke(request = GetBacklogListRequestModel(categoryId = categoryId, page = page, size = size)).collect {
                resultResponse(it, ::onSuccessGetBacklogList)
            }
        }
    }

    private fun onSuccessGetBacklogList(response: BacklogListModel) {
        updateSnapshotList(response.backlogs)

        val backlogs: List<TodoItemModel> = response.backlogs.map { it.apply { categoryId = uiState.value.selectedCategoryId } }

        updateState(
            uiState.value.copy(
                backlogList = backlogs,
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
            createBacklogUseCase.invoke(request = CreateBacklogRequestModel(uiState.value.selectedCategoryId, content)).collect {
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

        getBacklogList(categoryId = -1, page = 0, size = uiState.value.backlogList.size)
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

    fun updateCategory(todoId: Long, categoryId: Long?) {
        Timber.d("[수정 테스트] ${uiState.value.backlogList} -> $todoId $categoryId")
//        val newList = uiState.value.backlogList.filter { it.todoId != todoId }
//        updateList(newList)

        viewModelScope.launch {
            updateTodoCategoryUseCase(request = UpdateTodoCategoryModel(
                todoId = todoId,
                todoCategoryModel = TodoCategoryIdModel(categoryId)
            )).collect {
                resultResponse(it, {
//                    updateSnapshotList(uiState.value.backlogList)
                    getBacklogList(categoryId = uiState.value.selectedCategoryId, page = 0, size = uiState.value.backlogList.size)
                }, { error ->
                    Timber.d("[카테고리] 수정 서버통신 실패 -> $error")
                })
            }
        }
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

    fun getSelectedItemDetailContent(item: TodoItemModel, callback: (TodoItemModel) -> Unit) {
        viewModelScope.launch {
            getTodoDetailUseCase(item.todoId).collect {
                resultResponse(it, { data ->
                    callback(setSelectedItemCategory(item, data))
                }, { error ->
                    Timber.d("[todo] 할 일 상세조회 실패 -> $error")
                })
            }
        }
    }

    private fun setSelectedItemCategory(item: TodoItemModel, response: TodoDetailItemModel): TodoItemModel {
        val categoryId: Long =
            uiState.value.categoryList.firstOrNull { it.categoryName == response.categoryName && it.categoryImgUrl == response.emojiImageUrl }?.categoryId ?: -1
        val todoItem: TodoItemModel = item.copy(categoryId = categoryId)

        updateState(
            uiState.value.copy(
                selectedItem = todoItem,
            )
        )

        return todoItem
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

    fun updateTodoRepeat(id: Long) {
        updateTodoRepeatInUI(id)

        viewModelScope.launch {
            updateTodoRepeatUseCase(id).collect {
                resultResponse(it, { updateSnapshotList(uiState.value.backlogList) }, { onFailedUpdateBacklogList() })
            }
        }
    }

    private fun updateTodoRepeatInUI(id: Long) {
        val newList = uiState.value.backlogList.map {
            if (it.todoId == id) {
                it.copy(isRepeat = !it.isRepeat)
            } else {
                it
            }
        }
        val updatedItem = uiState.value.selectedItem.copy(isRepeat = !uiState.value.selectedItem.isRepeat)

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