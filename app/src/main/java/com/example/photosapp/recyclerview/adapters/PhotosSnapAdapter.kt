package com.example.photosapp.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.Photo
import com.example.photosapp.databinding.PhotoExtendedItemBinding
import com.example.photosapp.recyclerview.viewholders.PhotosExtendedViewHolder

class PhotosSnapAdapter: ListAdapter<Photo, PhotosExtendedViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosExtendedViewHolder {
        return PhotosExtendedViewHolder(PhotoExtendedItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotosExtendedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bin(item)
    }
}