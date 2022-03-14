package com.example.apilogger

import com.google.gson.annotations.SerializedName

class DomainEntity<T>(
    @SerializedName("code")
    val code: Int,

    @SerializedName("msg")
    val message: String,

    @SerializedName("data")
    val data: T
)