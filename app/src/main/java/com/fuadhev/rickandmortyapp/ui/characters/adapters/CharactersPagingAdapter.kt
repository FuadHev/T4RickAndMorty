package com.fuadhev.rickandmortyapp.ui.characters.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fuadhev.rickandmortyapp.R
import com.fuadhev.rickandmortyapp.common.utils.GenericDiffUtil
import com.fuadhev.rickandmortyapp.data.network.dto.Result
import com.fuadhev.rickandmortyapp.databinding.ItemCharacterBinding
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel
import com.google.android.material.imageview.ShapeableImageView

class CharactersPagingAdapter :
    PagingDataAdapter<CharactersUiModel, CharactersPagingAdapter.CharactersViewHolder>(
        GenericDiffUtil(
            myItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            myContentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )) {

    lateinit var onClick : (CharactersUiModel,ShapeableImageView) ->Unit

    inner class CharactersViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharactersUiModel) {

            with(binding){


                imgCharacter.load(item.image){
                    placeholder(R.drawable.preloading_animation)
                }
                imgCharacter.transitionName=item.image

                txtCharacterName.text=item.name
                txtCharacterSpecies.text=item.species
                root.setOnClickListener {
                    onClick(item,imgCharacter)
                }
                when(item.gender){
                    "Female"->{
                        icGender.setBackgroundResource(R.drawable.bg_gender_human)
                        icGender.setImageResource(R.drawable.ic_female)

                    }
                    "Male"->{
                        icGender.setBackgroundResource(R.drawable.bg_gender_human)
                        icGender.setImageResource(R.drawable.ic_male)
                    }
                    "Genderless"->{
                        icGender.setBackgroundColor(Color.RED)
                        icGender.setImageResource(R.drawable.ic_genderless)
                    }
                    "unknown"->{
                        icGender.setBackgroundColor(Color.GRAY)
                        icGender.setImageResource(R.drawable.ic_unknown)
                    }

                }
            }

        }

    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(view)
    }
}