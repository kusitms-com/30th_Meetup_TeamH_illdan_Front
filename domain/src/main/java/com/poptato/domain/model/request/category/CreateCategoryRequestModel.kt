package com.poptato.domain.model.request.category

data class CreateCategoryRequestModel (
    val name: String = "",
    val emojiId: Long = -1
)