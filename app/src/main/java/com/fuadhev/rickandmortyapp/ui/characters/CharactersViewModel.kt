package com.fuadhev.rickandmortyapp.ui.characters

import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel
import com.fuadhev.rickandmortyapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repo: Repository) : ViewModel() {


    private val _characterState = MutableStateFlow<CharactersUiState>(CharactersUiState.Loading)
    val characterState get() = _characterState


    var charactersData: Flow<PagingData<CharactersUiModel>>

    init {
       charactersData=getCharacters()
    }
    fun getCharacters()=
       repo.getCharacters()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty()).cachedIn(viewModelScope)


    fun getCharactersByFilter(
        name: String = "",
        status: String = "",
        gender: String = ""
    ) {
        try {
            viewModelScope.launch {
                repo.getCharactersByFilter(name, status, gender).cachedIn(viewModelScope)
                    .collectLatest {
                        characterState.value = CharactersUiState.SuccessSearchData(it)
                    }
            }

        } catch (e: Exception) {
            characterState.value = CharactersUiState.Error(e.localizedMessage ?: "Error 404")

        }


    }

}