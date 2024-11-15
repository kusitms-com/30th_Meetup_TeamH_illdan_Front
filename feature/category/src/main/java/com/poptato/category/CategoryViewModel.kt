package com.poptato.category

import com.poptato.domain.model.response.category.CategoryIconItemModel
import com.poptato.domain.model.response.category.CategoryIconTotalListModel
import com.poptato.domain.model.response.category.CategoryIconTypeListModel
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

): BaseViewModel<CategoryPageState>(
    CategoryPageState()
) {

    private val _categoryIconList: CategoryIconTotalListModel = CategoryIconTotalListModel(
        listOf(
            CategoryIconTypeListModel(
                "생산성",
                listOf(
                    CategoryIconItemModel(1, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                    CategoryIconItemModel(2, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                    CategoryIconItemModel(3, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                    CategoryIconItemModel(4, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                    CategoryIconItemModel(5, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                    CategoryIconItemModel(6, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                    CategoryIconItemModel(7, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                    CategoryIconItemModel(8, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                )
            ),
            CategoryIconTypeListModel(
                "데일리",
                listOf(
                    CategoryIconItemModel(9, "https://github.com/user-attachments/assets/dc389ca0-fe85-44e5-9371-d3bc3505b53e"),
                )
            )
        )
    )

    init {
        getCategoryIconList()
    }

    fun onValueChange(newValue: String) {
        updateState(
            uiState.value.copy(
                categoryName = newValue
            )
        )
    }

    private fun getCategoryIconList() {
        // TODO 서버통신 연결
        updateState(
            uiState.value.copy(
                categoryIconList = _categoryIconList
            )
        )
    }

    fun getSelectedIcon(icon: CategoryIconItemModel) {
        updateState(
            uiState.value.copy(
                selectedIcon = icon
            )
        )
    }

    fun finishSettingCategory() {
        // TODO 카테고리 설정 서버통신 연결
        Timber.d("[카테고리] 카테고리명: ${uiState.value.categoryName} && 아이콘: ${uiState.value.selectedIcon?.iconId}")
    }
}