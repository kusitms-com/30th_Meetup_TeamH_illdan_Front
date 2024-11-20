package com.poptato.category

import androidx.lifecycle.viewModelScope
import com.poptato.domain.model.request.category.CreateCategoryRequestModel
import com.poptato.domain.model.response.category.CategoryIconItemModel
import com.poptato.domain.model.response.category.CategoryIconTotalListModel
import com.poptato.domain.model.response.category.CategoryScreenContentModel
import com.poptato.domain.usecase.category.CreateCategoryUseCase
import com.poptato.domain.usecase.category.GetCategoryIconListUseCase
import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryIconListUseCase: GetCategoryIconListUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase
): BaseViewModel<CategoryPageState>(
    CategoryPageState()
) {

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
        viewModelScope.launch {
            getCategoryIconListUseCase(request = Unit).collect {
                resultResponse(it, { data ->
                    onSuccessGetCategoryIconList(data)
                    Timber.d("[카테고리] 아이콘 리스트 서버통신 성공 -> $data")
                }, { error ->
                    Timber.d("[카테고리] 아이콘 리스트 서버통신 실패 -> ${error.message}")
                })
            }
        }
    }

    private fun onSuccessGetCategoryIconList(response: CategoryIconTotalListModel) {
        updateState(
            uiState.value.copy(
                categoryIconList = response
            )
        )
    }

    fun getSelectedIcon(icon: CategoryIconItemModel) {
        updateState(
            uiState.value.copy(
                selectedIcon = icon,
                categoryIconImgUrl = icon.iconImgUrl
            )
        )
    }

    fun getModifyIconItem(item: CategoryScreenContentModel) {
        updateState(
            uiState.value.copy(
                screenType = item.screenType,
                categoryName = item.categoryItem.categoryName,
                categoryIconImgUrl = item.categoryItem.categoryImgUrl
            )
        )
    }

    fun finishSettingCategory() {
        viewModelScope.launch {
            createCategoryUseCase(request = CreateCategoryRequestModel(uiState.value.categoryName, uiState.value.selectedIcon?.iconId ?: -1)).collect {
                resultResponse(it, { data ->
                    Timber.d("[카테고리] 카테고리 생성 서버통신 성공 -> $data")
                }, { error ->
                    Timber.d("[카테고리] 카테고리 생성 서버통신 실패 -> $error")
                })
            }
        }
    }
}