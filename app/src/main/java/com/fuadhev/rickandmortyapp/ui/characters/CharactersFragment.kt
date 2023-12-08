package com.fuadhev.rickandmortyapp.ui.characters

import android.content.Context
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.fuadhev.rickandmortyapp.R
import com.fuadhev.rickandmortyapp.common.base.BaseFragment
import com.fuadhev.rickandmortyapp.common.utils.Extensions.gone
import com.fuadhev.rickandmortyapp.common.utils.Extensions.visible
import com.fuadhev.rickandmortyapp.common.utils.isOnline
import com.fuadhev.rickandmortyapp.databinding.FragmentCharactersBinding
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel
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

    private lateinit var statusAdapter: ArrayAdapter<String>
    private lateinit var genderAdapter: ArrayAdapter<String>

    lateinit var characterList: PagingData<CharactersUiModel>

    private var currentStatus = ""
    private var gender = ""


    override fun observeEvents() {

        lifecycleScope.launch {
            viewModel.charactersData.collectLatest {
                characterAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                characterList = it
            }
        }
        lifecycleScope.launch {
            viewModel.characterState.flowWithLifecycle(lifecycle).collectLatest {
                when (it) {
                    is CharactersUiState.Loading -> {

                    }

                    is CharactersUiState.SuccessSearchData -> {
                        characterAdapter.submitData(viewLifecycleOwner.lifecycle, it.list)
                    }

                    is CharactersUiState.Error -> {

                    }

                }

            }
        }
//        if (isOnline(requireContext())) {
//            lifecycleScope.launch {
//                viewModel.getCharacters().collectLatest {
//                    if (binding.searchView.text.isEmpty()) {
//                        characterAdapter.submitData(viewLifecycleOwner.lifecycle, it)
//                    }
//                    characterList = it
//                    binding.txtConnectionError.gone()
//                }
//            }
//        } else {
//            binding.txtConnectionError.visible()
//        }

    }

    private fun searchNews() {
        with(binding) {

            searchView.doAfterTextChanged {
                if (it.toString().trim() == "") {
                    characterAdapter.submitData(viewLifecycleOwner.lifecycle, characterList)
                }
            }
            searchView.setOnEditorActionListener { text, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    viewModel.getCharactersByFilter(text.text.toString(), currentStatus, gender)

                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(text.windowToken, 0)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }

    }

    override fun setupListeners() {
        characterAdapter.onClick = { charData, img ->
            val extras = FragmentNavigatorExtras(
                img to charData.image!!
            )

            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                    charData
                ), extras
            )
        }

        postponeEnterTransition()
        binding.rvCharacter.doOnPreDraw {
            startPostponedEnterTransition()
        }

    }

    override fun onResume() {
        super.onResume()

        val genders = resources.getStringArray(R.array.genderArray)
        val statusList = resources.getStringArray(R.array.statusArray)

        binding.genderType.setAdapter(null)
        val genderAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, genders)
        binding.genderType.setAdapter(genderAdapter)

        binding.status.setAdapter(null)
        val statusAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, statusList)
        binding.status.setAdapter(statusAdapter)
    }

    override fun onCreateFinish() {
        searchNews()
        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        setAdapter()
        val genders = resources.getStringArray(R.array.genderArray)
        genderAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, genders)

        binding.genderType.setOnItemClickListener { _, _, pos, _ ->
            gender = genders[pos]
            if (gender == "all") {
                gender = ""
                viewModel.getCharactersByFilter(
                    name = binding.searchView.text.toString(),
                    currentStatus,
                    gender
                )
            } else {
                viewModel.getCharactersByFilter(
                    binding.searchView.text.toString(),
                    currentStatus,
                    gender
                )
            }

        }
        binding.genderType.setAdapter(genderAdapter)


        val statusList = resources.getStringArray(R.array.statusArray)
        statusAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown, statusList)
        binding.status.setOnItemClickListener { _, _, pos, _ ->
            currentStatus = statusList[pos]
            if (currentStatus == "all") {
                currentStatus = ""
                viewModel.getCharactersByFilter(
                    binding.searchView.text.toString(),
                    currentStatus,
                    gender
                )
            } else {
                viewModel.getCharactersByFilter(
                    binding.searchView.text.toString(),
                    currentStatus,
                    gender
                )

            }
        }
        binding.status.setAdapter(statusAdapter)


    }

    private fun setAdapter() {
        binding.rvCharacter.layoutManager = GridLayoutManager(activity, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (characterAdapter.itemCount == position) 2 else 1
                }
            }
        }
        binding.rvCharacter.adapter = characterAdapter.withLoadStateFooter(
            footer = CharactersLoadStateAdapter { characterAdapter.retry() }
        )


    }


}