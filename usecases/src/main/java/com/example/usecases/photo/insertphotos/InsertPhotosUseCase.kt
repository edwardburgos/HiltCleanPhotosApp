package com.example.usecases.photo.insertphotos

import com.example.domain.Photo

interface InsertPhotosUseCase {
    operator fun invoke(photos: List<Photo>)
}