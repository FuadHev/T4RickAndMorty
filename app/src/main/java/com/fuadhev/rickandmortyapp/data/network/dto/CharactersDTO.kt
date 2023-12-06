package com.fuadhev.rickandmortyapp.data.network.dto


import com.google.gson.annotations.SerializedName

data class CharactersDTO(
    val info: Info,
    @SerializedName("results")
    val results: List<Result>
)