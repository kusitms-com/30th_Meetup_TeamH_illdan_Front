package com.poptato.category

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    }
}