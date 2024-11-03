package com.poptato.data.base

object Endpoints {

    object Auth {
        private const val AUTH = "/auth"
        const val LOGIN = "$AUTH/login"
        const val REFRESH = "$AUTH/refresh"
        const val LOGOUT = "$AUTH/logout"
    }

    object Backlog {
        const val BACKLOG = "/backlog"
        const val BACKLOGS = "/backlogs"
    }

    object Yesterday {
        const val YESTERDAY = "/yesterdays"
    }

    object History {
        const val HISTORIES = "/histories"
    }

    object Todo {
        private const val TODO = "/todo"
        const val DELETE = "$TODO/{todoId}"
        const val MODIFY = "$DELETE/content"
        const val DRAG_DROP = "/dragAndDrop"
        const val DEADLINE = "$DELETE/deadline"
        const val BOOKMARK = "$DELETE/bookmark"
        const val SWIPE = "swipe"
        const val COMPLETION = "$DELETE/achieve"
    }

    object MyPage {
        private const val USER = "/user"
        const val USER_DELETE = USER
        const val USER_DATA = "$USER/mypage"
        const val POLICY = "/policy"
    }

    object Today {
        const val TODAYS = "/todays"
    }
}
