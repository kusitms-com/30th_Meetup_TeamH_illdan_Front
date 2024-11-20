package com.poptato.domain.repository

import com.poptato.domain.model.request.category.CategoryIdModel
import com.poptato.domain.model.request.category.CreateCategoryRequestModel
import com.poptato.domain.model.response.category.CategoryIconTotalListModel
import com.poptato.domain.model.response.category.CategoryListModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategoryIcon(): Flow<Result<CategoryIconTotalListModel>>
    suspend fun createCategory(request: CreateCategoryRequestModel): Flow<Result<CategoryIdModel>>
    suspend fun getCategoryList(page: Int, size: Int): Flow<Result<CategoryListModel>>
}