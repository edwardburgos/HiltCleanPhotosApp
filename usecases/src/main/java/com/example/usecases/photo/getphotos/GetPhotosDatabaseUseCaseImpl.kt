package com.example.usecases.photo.getphotos

import com.example.data.repository.PhotoRepositoryImpl
import javax.inject.Inject

class GetPhotosDatabaseUseCaseImpl @Inject constructor(private val photoRepository: PhotoRepositoryImpl):
    GetPhotosDatabaseUseCase {
    override operator fun invoke() = photoRepository.getPhotosFromDatabase()
}