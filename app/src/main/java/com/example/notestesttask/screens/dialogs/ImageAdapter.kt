package com.example.notestesttask.screens.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.notestesttask.R
import com.example.notestesttask.databinding.ItemImageBinding
import com.example.notestesttask.model.Image

class ImageAdapter(
    private var imageList: MutableList<Image>,
    private var onImageClick: (url: String) -> Unit
) :
    RecyclerView.Adapter<ImageAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = DataBindingUtil.inflate<ItemImageBinding>(
            LayoutInflater.from(parent.context), R.layout.item_image, parent, false
        )
        return NotesViewHolder(binding, onImageClick)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    class NotesViewHolder constructor(
        private var binding: ItemImageBinding,
        private var onImageClick: (url: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            Glide.with(binding.root.context)
                .load(image.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivImage)
            binding.ivImage.setOnClickListener {
                onImageClick(image.imageUrl)
            }
        }
    }
}