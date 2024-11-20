package com.poptato.data.service

import com.poptato.data.base.ApiResponse
import com.poptato.data.base.Endpoints
import com.poptato.data.model.response.category.CategoryIdResponse
import com.poptato.data.model.response.category.CategoryIconTotalListResponse
import com.poptato.data.model.response.category.CategoryListResponse
import com.poptato.domain.model.request.category.CreateCategoryRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CategoryService {

    @GET(Endpoints.Category.ICONS)
    suspend fun getCategoryIcon(): Response<ApiResponse<CategoryIconTotalListResponse>>

    @POST(Endpoints.Category.CATEGORY)
    suspend fun createCategory(
        @Body request: CreateCategoryRequestModel
    ): Response<ApiResponse<CategoryIdResponse>>

    @GET(Endpoints.Category.LIST)
    suspend fun getCategoryList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ApiResponse<CategoryListResponse>>
}