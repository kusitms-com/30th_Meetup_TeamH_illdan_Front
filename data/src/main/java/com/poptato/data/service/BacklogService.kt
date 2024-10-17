package com.poptato.data.service

import com.poptato.data.base.ApiResponse
import com.poptato.data.base.Endpoints
import com.poptato.domain.model.request.backlog.CreateBacklogRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BacklogService {

    @POST(Endpoints.Backlog.BACKLOG)
    suspend fun createBacklog(
        @Body request: CreateBacklogRequestModel
    ): Response<ApiResponse<Unit>>
}