package com.poptato.data.repository

import com.poptato.data.base.BaseRepository
import com.poptato.data.mapper.CategoryIconResponseMapper
import com.poptato.data.service.CategoryService
import com.poptato.domain.model.response.category.CategoryIconTotalListModel
import com.poptato.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService
): CategoryRepository, BaseRepository() {

    override suspend fun getCategoryIcon(): Flow<Result<CategoryIconTotalListModel>> {
        return apiLaunch(apiCall = { categoryService.getCategoryIcon() }, CategoryIconResponseMapper)
    }
}