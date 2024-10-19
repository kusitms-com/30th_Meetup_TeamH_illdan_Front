package com.poptato.data.mapper

import com.poptato.data.base.Mapper
import com.poptato.data.model.response.today.TodayListResponse
import com.poptato.domain.model.response.today.TodayListModel
import com.poptato.domain.model.response.today.TodoItemModel

object TodayListResponseMapper: Mapper<TodayListResponse, TodayListModel> {
    override fun responseToModel(response: TodayListResponse?): TodayListModel {
        return response?.let {
            TodayListModel(
                date = it.date,
                todays = response.todays.map { item ->
                    TodoItemModel(
                        todoId = item.todoId,
                        content = item.content,
                        todoStatus = item.todoStatus,
                        isBookmark = item.isBookmark,
                        dDay = item.deadline
                    )
                },
                totalPageCount = it.totalPageCount
            )
        } ?: TodayListModel()
    }
}