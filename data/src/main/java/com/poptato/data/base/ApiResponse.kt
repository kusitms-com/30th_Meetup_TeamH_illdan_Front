package com.poptato.data.base

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("status")
    val status: Int,

    @SerializedName("data")
    val data: T,

    @SerializedName("timestamp")
    val timestamp: String
)