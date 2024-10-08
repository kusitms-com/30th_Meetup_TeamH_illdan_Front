package com.poptato.domain.model.response.today

data class TodayListModel(
    val date: String = "",
    val todays: List<TodayItemModel> = emptyList(),
    val totalPageCount: Int = -1
)
