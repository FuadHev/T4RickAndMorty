package com.fuadhev.rickandmortyapp.data.network.api

import com.fuadhev.rickandmortyapp.data.network.dto.CharactersDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int):Response<CharactersDTO>

}