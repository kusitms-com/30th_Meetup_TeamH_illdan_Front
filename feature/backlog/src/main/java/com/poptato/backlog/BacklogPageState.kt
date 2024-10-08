package com.poptato.backlog

import com.poptato.ui.base.PageState

data class BacklogPageState(
    val backlogList: List<String> = emptyList(),
    val taskInput: String = ""
): PageState