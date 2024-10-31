package com.poptato.feature

import com.poptato.core.enums.BottomNavType
import com.poptato.domain.model.enums.BottomSheetType
import com.poptato.domain.model.response.today.TodoItemModel
import com.poptato.ui.base.PageState

data class MainPageState(
    val bottomNavType: BottomNavType = BottomNavType.DEFAULT,
    val selectedTodoItem: TodoItemModel = TodoItemModel(),
    val bottomSheetType: BottomSheetType = BottomSheetType.Main,
    val backPressedOnce: Boolean = false
): PageState