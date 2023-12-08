package com.fuadhev.rickandmortyapp.ui.characters

import androidx.paging.PagingData
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel
import kotlinx.coroutines.flow.Flow

sealed class CharactersUiState {
    object Loading : CharactersUiState()
    data class SuccessSearchData(val list :PagingData<CharactersUiModel>):CharactersUiState()



    data class Error(val message: String) : CharactersUiState()
}
