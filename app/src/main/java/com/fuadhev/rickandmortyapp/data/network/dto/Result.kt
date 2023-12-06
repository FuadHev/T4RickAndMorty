package com.fuadhev.rickandmortyapp.data.network.dto


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
):Serializable