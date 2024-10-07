package com.poptato.backlog

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BacklogViewModel @Inject constructor(

) : BaseViewModel<BacklogPageState>(
    BacklogPageState()
) {

}