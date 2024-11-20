package com.poptato.data.mapper

import com.poptato.data.base.Mapper
import com.poptato.data.model.response.category.CategoryTotalListResponse
import com.poptato.domain.model.response.category.CategoryIconItemModel
import com.poptato.domain.model.response.category.CategoryIconTotalListModel
import com.poptato.domain.model.response.category.CategoryIconTypeListModel

object CategoryIconResponseMapper : Mapper<CategoryTotalListResponse, CategoryIconTotalListModel> {
    override fun responseToModel(response: CategoryTotalListResponse?): CategoryIconTotalListModel {
        return response?.let {
            CategoryIconTotalListModel(
                icons = it.groupEmojis.map { typeItem ->
                    CategoryIconTypeListModel(
                        iconType = typeItem.iconType,
                        icons = typeItem.icons.map { item ->
                            CategoryIconItemModel(
                                iconId = item.iconId,
                                iconImgUrl = item.iconImgUrl
                            )
                        }
                    )
                }
            )
        } ?: CategoryIconTotalListModel()
    }
}