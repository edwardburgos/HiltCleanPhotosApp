package com.example.usecases.photo.getphotos

import com.example.data.repository.PhotoRepositoryImpl
import javax.inject.Inject

class GetPhotosApiUseCaseImpl @Inject constructor(private val photoRepository: PhotoRepositoryImpl): GetPhotosApiUseCase {
    override suspend operator fun invoke() = photoRepository.getPhotosFromApi()
}