package com.example.photosapp.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.Photo
import com.example.photosapp.databinding.PhotoExtendedItemBinding

class PhotosSnapAdapter(): ListAdapter<Photo, PhotosExtendedViewHolder>(DiffCallback) {
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
//        val viewHolder: PhotosExtendedViewHolder by inject {
//            parametersOf(PhotoExtendedItemBinding.inflate(LayoutInflater.from(parent.context)))
//        }
        // return viewHolder
        //binding.photosSnap.adapter = PhotosSnapAdapter()
        return PhotosExtendedViewHolder(PhotoExtendedItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotosExtendedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bin(item)
    }
}