package com.poptato.data.mapper

import com.poptato.data.base.Mapper
import com.poptato.data.model.response.backlog.TodoIdResponse

object TodoIdResponseMapper: Mapper<TodoIdResponse, TodoIdModel> {
    override fun responseToModel(response: TodoIdResponse?): TodoIdModel {
        return response?.let {
            TodoIdModel(todoId = it.todoId)
        } ?: TodoIdModel()
    }
}