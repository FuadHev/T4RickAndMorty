package com.fuadhev.rickandmortyapp.data.network.dto


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    val name: String,
    val url: String
): Serializable