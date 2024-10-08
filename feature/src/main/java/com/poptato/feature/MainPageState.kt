package com.poptato.feature

import com.poptato.core.enums.BottomNavType
import com.poptato.ui.base.PageState

data class MainPageState(
    val bottomNavType: BottomNavType = BottomNavType.DEFAULT
): PageState