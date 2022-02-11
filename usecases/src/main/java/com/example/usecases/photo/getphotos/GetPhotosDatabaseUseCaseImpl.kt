package com.example.usecases.photo.getphotos

import com.example.data.repository.PhotoRepositoryImpl
import javax.inject.Inject

class GetPhotosDatabaseUseCaseImpl @Inject constructor(private val photoRepository: PhotoRepositoryImpl):
    GetPhotosDatabaseUseCase {
    override suspend operator fun invoke() = photoRepository.getPhotosFromDatabase()
}