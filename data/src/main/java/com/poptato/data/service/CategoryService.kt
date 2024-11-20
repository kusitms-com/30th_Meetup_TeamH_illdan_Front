package com.poptato.data.service

import com.poptato.data.base.ApiResponse
import com.poptato.data.base.Endpoints
import com.poptato.data.model.response.category.CategoryIdResponse
import com.poptato.data.model.response.category.CategoryTotalListResponse
import com.poptato.domain.model.request.category.CreateCategoryRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryService {

    @GET(Endpoints.Category.ICONS)
    suspend fun getCategoryIcon(): Response<ApiResponse<CategoryTotalListResponse>>

    @POST(Endpoints.Category.CATEGORY)
    suspend fun createCategory(
        @Body request: CreateCategoryRequestModel
    ): Response<ApiResponse<CategoryIdResponse>>
}