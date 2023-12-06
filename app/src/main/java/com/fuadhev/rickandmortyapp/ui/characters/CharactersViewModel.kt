package com.fuadhev.rickandmortyapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fuadhev.rickandmortyapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repo:Repository):ViewModel() {

    fun getCharacters() = repo.getCharacters().cachedIn(viewModelScope)

}