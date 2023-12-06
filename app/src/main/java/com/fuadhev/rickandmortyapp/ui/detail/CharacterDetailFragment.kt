package com.fuadhev.rickandmortyapp.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.fuadhev.rickandmortyapp.R
import com.fuadhev.rickandmortyapp.common.base.BaseFragment
import com.fuadhev.rickandmortyapp.databinding.FragmentCharacterDetailBinding
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.CharacterData
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding>(FragmentCharacterDetailBinding::inflate) {
    private val args by navArgs<CharacterDetailFragmentArgs>()

    override fun observeEvents() {
    }


    private fun setCharacterData(characterData: CharactersUiModel){
        binding.imgCharacter.transitionName=args.characterData.image
        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition(500, TimeUnit.MILLISECONDS)
        with(binding){
            imgCharacter.load(characterData.image){
                placeholder(R.drawable.preloading_animation)
            }
            txtGender.text=getString(R.string.gender_male,characterData.gender)
            txtStatus.text=getString(R.string.status,characterData.status)
            txtSpecies.text=getString(R.string.species,characterData.species)
            txtType.text=getString(R.string.type,characterData.type)
            txtOrigin.text=getString(R.string.origin,characterData.origin.name)

        }
    }

    override fun setupListeners() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onCreateFinish() {
        setCharacterData(args.characterData)



    }

}