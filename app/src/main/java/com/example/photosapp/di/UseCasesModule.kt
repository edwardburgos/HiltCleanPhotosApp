package com.example.photosapp.di

import com.example.usecases.photo.getphotos.GetPhotosApiUseCase
import com.example.usecases.photo.getphotos.GetPhotosApiUseCaseImpl
import com.example.usecases.photo.getphotos.GetPhotosDatabaseUseCase
import com.example.usecases.photo.getphotos.GetPhotosDatabaseUseCaseImpl
import com.example.usecases.photo.insertphotos.InsertPhotosUseCase
import com.example.usecases.photo.insertphotos.InsertPhotosUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Provides
    fun providesGetPhotosApiUseCaseImpl(getPhotosApiUseCaseImpl: GetPhotosApiUseCaseImpl): GetPhotosApiUseCase {
        return getPhotosApiUseCaseImpl
    }

    @Provides
    fun providesGetPhotosDatabaseUseCaseImpl(getPhotosDatabaseUseCaseImpl: GetPhotosDatabaseUseCaseImpl): GetPhotosDatabaseUseCase {
        return getPhotosDatabaseUseCaseImpl
    }

    @Provides
    fun providesInsertPhotosUseCaseImpl(insertPhotosUseCaseImpl: InsertPhotosUseCaseImpl): InsertPhotosUseCase {
        return insertPhotosUseCaseImpl
    }
}