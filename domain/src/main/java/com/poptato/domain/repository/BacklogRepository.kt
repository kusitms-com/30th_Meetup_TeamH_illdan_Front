package com.poptato.domain.repository

import com.poptato.domain.model.request.backlog.CreateBacklogRequestModel
import com.poptato.domain.model.response.backlog.BacklogListModel
import kotlinx.coroutines.flow.Flow

interface BacklogRepository {
    suspend fun createBacklog(request: CreateBacklogRequestModel): Flow<Result<Unit>>
    suspend fun getBacklogList(page: Int, size: Int): Flow<Result<BacklogListModel>>
}