package com.example.usecases.photo.insertphotos

import com.example.data.repository.PhotoRepositoryImpl
import com.example.domain.Photo
import javax.inject.Inject

class InsertPhotosUseCaseImpl @Inject constructor(private val photoRepository: PhotoRepositoryImpl): InsertPhotosUseCase {
    override operator fun invoke(photos: List<Photo>) = photoRepository.insertPhotos(photos)
}