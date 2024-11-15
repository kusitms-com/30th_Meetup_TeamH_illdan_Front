package com.poptato.category

import com.poptato.domain.model.response.category.CategoryIconItemModel
import com.poptato.domain.model.response.category.CategoryIconTotalListModel
import com.poptato.ui.base.PageState

data class CategoryPageState(
    val textInput: String = "",
    val categoryIconList: CategoryIconTotalListModel = CategoryIconTotalListModel(),
    val selectedIcon: CategoryIconItemModel? = null
): PageState