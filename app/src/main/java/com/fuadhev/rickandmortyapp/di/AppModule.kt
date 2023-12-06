package com.fuadhev.rickandmortyapp.di

import android.content.Context
import androidx.room.Room
import com.fuadhev.rickandmortyapp.common.utils.Constants.BASE_URL
import com.fuadhev.rickandmortyapp.data.network.api.CharacterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()).build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): CharacterApiService = retrofit.create(CharacterApiService::class.java)

//    @Singleton
//    @Provides
//    fun provideAuthRepository(apiService:NewsApiService,savedDao: SavedDAO): NewsRepository  = NewsRepository(apiService,savedDao)




}