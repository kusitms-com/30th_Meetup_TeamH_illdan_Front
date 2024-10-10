package com.poptato.today

import com.poptato.domain.model.response.today.TodayItemModel
import com.poptato.domain.model.response.today.TodayListModel
import com.poptato.ui.base.PageState

data class TodayPageState(
    val todayList: TodayListModel = TodayListModel(
        todays = emptyList()
    )
) : PageState
