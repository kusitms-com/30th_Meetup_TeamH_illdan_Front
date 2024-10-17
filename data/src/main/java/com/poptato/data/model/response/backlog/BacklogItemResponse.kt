package com.poptato.data.model.response.backlog

import com.google.gson.annotations.SerializedName

data class BacklogItemResponse(
    @SerializedName("todoId")
    val todoId: Long = -1,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("isBookmark")
    val isBookmark: Boolean = false,
    val deadline: String = "",
    @SerializedName("dDay")
    val dDay: Int = -1
)
