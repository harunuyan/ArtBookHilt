package com.volie.artbookhilttesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.volie.artbookhilttesting.databinding.ImageApiRowBinding
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    inner class ImageViewHolder(private val binding: ImageApiRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            val image = imageList[position]
            binding.apply {
                glide.load(image).into(imgSingleArt)
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(image)
                    }
                }
            }
        }
    }

    val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var imageList: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageApiRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindData(position)
    }
}