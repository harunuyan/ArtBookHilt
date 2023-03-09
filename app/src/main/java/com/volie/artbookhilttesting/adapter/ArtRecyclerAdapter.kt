package com.volie.artbookhilttesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.volie.artbookhilttesting.databinding.ArtRowBinding
import com.volie.artbookhilttesting.model.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.RecyclerViewHolder>() {
    inner class RecyclerViewHolder(private val binding: ArtRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            val art = artList[position]
            with(binding) {
                txtArtName.text = "Name : ${art.name}"
                txtArtistName.text = "Artist Name : ${art.artistName}"
                txtYear.text = "Year : ${art.year}"
                glide.load(art.imageUrl).into(this.imgArtRow)
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var artList: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val binding = ArtRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}