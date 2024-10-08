package com.poptato.domain.model.response.today

import com.poptato.domain.model.enums.TodoStatus

data class TodayItemModel(
    val todoId: Long = -1,
    val content: String = "",
    val todoStatus: TodoStatus = TodoStatus.INCOMPLETE,
    val isBookmark: Boolean = false
)
