package com.fuadhev.rickandmortyapp.data.network.dto


import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("error")
    val error: String
)