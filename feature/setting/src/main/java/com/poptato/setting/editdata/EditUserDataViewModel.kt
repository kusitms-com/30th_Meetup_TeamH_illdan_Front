package com.poptato.setting.editdata

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditUserDataViewModel @Inject constructor(

) : BaseViewModel<EditUserDataPageState>(
    EditUserDataPageState()
) {

}