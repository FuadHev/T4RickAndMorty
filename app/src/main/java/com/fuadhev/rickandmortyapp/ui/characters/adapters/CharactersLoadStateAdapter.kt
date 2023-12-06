package com.fuadhev.rickandmortyapp.ui.characters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.rickandmortyapp.databinding.PagingLoadStateBinding

class CharactersLoadStateAdapter(private val retry: ()-> Unit): LoadStateAdapter<CharactersLoadStateAdapter.LoadStateViewHolder>() {
    inner class LoadStateViewHolder(private val binding: PagingLoadStateBinding)
        : RecyclerView.ViewHolder(binding.root){

        init {
            binding.btnRetry.setOnClickListener{
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState){

            with(binding){
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                txtError.isVisible = loadState !is LoadState.Loading
            }
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = PagingLoadStateBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


}