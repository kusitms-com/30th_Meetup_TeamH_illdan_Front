package com.poptato.category

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

): BaseViewModel<CategoryPageState>(
    CategoryPageState()
) {

    fun onValueChange(newValue: String) {
        updateState(
            uiState.value.copy(
                textInput = newValue
            )
        )
        Timber.d("[카테고리] 카테고리명 입력 -> ${uiState.value.textInput}")
    }
}