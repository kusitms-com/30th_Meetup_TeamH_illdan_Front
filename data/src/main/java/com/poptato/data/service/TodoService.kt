package com.poptato.data.service

import com.poptato.data.base.ApiResponse
import com.poptato.data.base.Endpoints
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface TodoService {
    @DELETE(Endpoints.Todo.DELETE)
    suspend fun deleteTodo(
        @Path("todoId") todoId: Long
    ): Response<ApiResponse<Unit>>
}