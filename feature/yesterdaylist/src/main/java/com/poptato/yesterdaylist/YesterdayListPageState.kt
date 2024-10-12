package com.poptato.yesterdaylist

import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.ui.base.PageState

data class YesterdayListPageState(
    val yesterdayList: List<TodoItemModel> = emptyList()
) : PageState