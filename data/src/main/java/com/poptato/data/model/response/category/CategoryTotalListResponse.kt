package com.poptato.data.model.response.category

import com.google.gson.annotations.SerializedName
import com.poptato.domain.model.response.category.CategoryIconTypeListModel

data class CategoryTotalListResponse (
    @SerializedName("groupEmojis")
    val groupEmojis: List<CategoryIconTypeListModel> = emptyList(),
    @SerializedName("totalPageCount")
    val totalPageCount: Int = -1
)