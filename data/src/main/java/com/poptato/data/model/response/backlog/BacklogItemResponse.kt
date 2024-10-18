package com.poptato.data.model.response.backlog

import com.google.gson.annotations.SerializedName

data class BacklogItemResponse(
    @SerializedName("todoId")
    val todoId: Long = -1,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("bookmark")
    val isBookmark: Boolean = false,
    @SerializedName("deadline")
    val deadline: String = "",
    @SerializedName("dday")
    val dDay: Int = -1
)
