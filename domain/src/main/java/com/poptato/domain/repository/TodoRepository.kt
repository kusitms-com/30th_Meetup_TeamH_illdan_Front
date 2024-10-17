package com.poptato.domain.repository

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun deleteTodo(todoId: Long): Flow<Result<Unit>>
}