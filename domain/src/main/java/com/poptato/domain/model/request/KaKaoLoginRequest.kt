package com.poptato.domain.model.request

data class KaKaoLoginRequest(
    val socialType: String = "KAKAO",
    val accessToken: String = "",
    val mobileType: String = "ANDROID",
    val clientId: String = ""
)
