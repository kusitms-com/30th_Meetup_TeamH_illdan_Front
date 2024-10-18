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

    object MyPage {
        private const val USER = "/user"
        const val USER_DELETE = USER
        const val USER_DATA = "$USER/mypage"
    }
}