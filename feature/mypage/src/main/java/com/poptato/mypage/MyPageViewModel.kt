package com.poptato.mypage

import com.poptato.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

): BaseViewModel<MyPagePageState>(
    MyPagePageState()
) {
}