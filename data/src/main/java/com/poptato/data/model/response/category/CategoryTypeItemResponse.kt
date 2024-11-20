package com.poptato.data.model.response.category

data class CategoryTypeItemResponse (
    val categoryType: String = "",
    val categoryIconItemList: List<CategoryIconItemResponse> = emptyList()
)