package com.fuadhev.rickandmortyapp.data.network.dto


data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)