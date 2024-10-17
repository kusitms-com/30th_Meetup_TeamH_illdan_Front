package com.potato.history.model

data class HistoryItemModel(
    val id: Long,
    val title: String,
    val date: String,
    val isChecked: Boolean
)