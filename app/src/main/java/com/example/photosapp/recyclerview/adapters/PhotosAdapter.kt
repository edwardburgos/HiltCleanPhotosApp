package com.example.photosapp.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.Photo
import com.example.photosapp.R
import com.example.photosapp.databinding.PhotoItemBinding
import com.example.photosapp.databinding.PhotoItemOptionalBinding
import com.example.photosapp.recyclerview.viewholders.ViewHolders

import java.lang.IllegalArgumentException

class PhotosAdapter(private val onClickListener: OnClickListener): ListAdapter<Photo, ViewHolders>(
    DiffCallback
) {

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
    ): ViewHolders {
        when (viewType) {
            R.layout.photo_item ->
                return ViewHolders.PhotoViewHolder(
                    PhotoItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            R.layout.photo_item_optional ->
                return ViewHolders.PhotoOptionalViewHolder(
                    PhotoItemOptionalBinding.inflate(
                        LayoutInflater.from(parent.context)
                    )
                )
            else -> throw IllegalArgumentException("Invalid viewType provided")
        }
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        when (holder) {
            is ViewHolders.PhotoViewHolder -> holder.bin(item)
            is ViewHolders.PhotoOptionalViewHolder -> holder.bin(item)
        }
    }

    class OnClickListener(val clickListener: (photo: Photo) -> Unit) {
        fun onClick(photo: Photo) = clickListener(photo)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) R.layout.photo_item else R.layout.photo_item_optional
    }
}