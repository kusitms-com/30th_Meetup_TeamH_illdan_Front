package com.poptato.yesterdaylist

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YesterdayListViewModel @Inject constructor(

): BaseViewModel<YesterdayListPageState>(
    YesterdayListPageState()
) {
}