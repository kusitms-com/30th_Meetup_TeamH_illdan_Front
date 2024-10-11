package com.potato.history.model

data class HistoryGroupedItem(
    val date: String,
    val items: List<HistoryItemModel>
)