package com.poptato.today

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(

) : BaseViewModel<TodayPageState>(TodayPageState()) {

}