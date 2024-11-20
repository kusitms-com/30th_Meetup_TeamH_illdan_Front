package com.poptato.data.service

import com.poptato.data.base.ApiResponse
import com.poptato.data.base.Endpoints
import com.poptato.data.model.response.category.CategoryTotalListResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {

    @GET(Endpoints.Category.ICONS)
    suspend fun getCategoryIcon(): Response<ApiResponse<CategoryTotalListResponse>>
}