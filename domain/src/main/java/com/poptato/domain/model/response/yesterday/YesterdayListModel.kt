package com.poptato.domain.model.response.yesterday

data class YesterdayListModel (
    val yesterdays: List<YesterdayListModel> = emptyList(),
    val totalPageCount: Int = -1
)