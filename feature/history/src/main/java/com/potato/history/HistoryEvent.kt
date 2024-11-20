package com.potato.history

import com.poptato.ui.base.Event

sealed class HistoryEvent: Event {
    data object HasEvent : HistoryEvent()
    data object FutureEvent : HistoryEvent()
    data object NoEvent : HistoryEvent()
}