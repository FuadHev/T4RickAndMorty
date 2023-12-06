package com.fuadhev.rickandmortyapp.ui.characters

import android.transition.TransitionInflater
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fuadhev.rickandmortyapp.common.base.BaseFragment
import com.fuadhev.rickandmortyapp.common.utils.Extensions.gone
import com.fuadhev.rickandmortyapp.common.utils.Extensions.visible
import com.fuadhev.rickandmortyapp.common.utils.isOnline
import com.fuadhev.rickandmortyapp.databinding.FragmentCharactersBinding
import com.fuadhev.rickandmortyapp.ui.characters.adapters.CharactersLoadStateAdapter
import com.fuadhev.rickandmortyapp.ui.characters.adapters.CharactersPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {
    private val viewModel by viewModels<CharactersViewModel>()
    private val characterAdapter by lazy {
        CharactersPagingAdapter()
    }
    override fun observeEvents() {
        if (isOnline(requireContext())){
            lifecycleScope.launch {
                viewModel.getCharacters().flowWithLifecycle(lifecycle).collectLatest {
                    characterAdapter.submitData(viewLifecycleOwner.lifecycle,it)
                    binding.txtConnectionError.gone()
                }
            }
        }else{
            binding.txtConnectionError.visible()
        }

    }

    override fun setupListeners() {
        characterAdapter.onClick={charData,img->
            val extras = FragmentNavigatorExtras(
                img to charData.image!!
            )
            findNavController().navigate(CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(charData),extras)
        }

        postponeEnterTransition()
        binding.rvCharacter.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }
    override fun onCreateFinish() {
        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        setAdapter()


    }

    private fun setAdapter(){
        binding.rvCharacter.layoutManager = GridLayoutManager(activity, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (characterAdapter.itemCount == position) 2 else 1
                }
            }
        }
        binding.rvCharacter.adapter=characterAdapter.withLoadStateFooter(
            footer = CharactersLoadStateAdapter { characterAdapter.retry() }
        )



    }


}