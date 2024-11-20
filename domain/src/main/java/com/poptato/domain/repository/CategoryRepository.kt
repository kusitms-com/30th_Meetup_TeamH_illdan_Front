package com.poptato.domain.repository

import com.poptato.domain.model.response.category.CategoryIconTotalListModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategoryIcon(): Flow<Result<CategoryIconTotalListModel>>
}