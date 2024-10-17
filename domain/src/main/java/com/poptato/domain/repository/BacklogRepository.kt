package com.poptato.domain.repository

import com.poptato.domain.model.request.backlog.CreateBacklogRequestModel
import kotlinx.coroutines.flow.Flow

interface BacklogRepository {
    suspend fun createBacklog(request: CreateBacklogRequestModel): Flow<Result<Unit>>
}