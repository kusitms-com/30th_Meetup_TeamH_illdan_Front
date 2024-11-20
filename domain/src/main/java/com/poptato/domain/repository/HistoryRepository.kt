package com.poptato.domain.repository

import com.poptato.domain.model.response.backlog.BacklogListModel
import com.poptato.domain.model.response.history.HistoryListModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun getHistoryList(page: Int, size: Int, date:String): Flow<Result<HistoryListModel>>
}