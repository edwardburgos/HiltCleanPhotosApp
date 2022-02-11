package com.example.photosapp.di

import android.content.Context
import com.example.data.database.PhotoDao
import com.example.data.database.PhotosDatabase
import com.example.data.database.model.PhotoDatabase
import com.example.data.database.model.PhotoDatabaseMapper
import com.example.domain.Photo
import com.example.domain.utils.DomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun providesPhotosDatabase(@ApplicationContext appContext: Context): PhotosDatabase {
        return PhotosDatabase.getInstance(appContext)
    }

    @Provides
    fun providesPhotoDao(photosDatabase: PhotosDatabase): PhotoDao {
        return photosDatabase.photoDao
    }

    @Singleton
    @Provides
    fun providesPhotoDatabaseMapper(photoDatabaseMapper: PhotoDatabaseMapper): DomainMapper<PhotoDatabase, Photo> {
        return photoDatabaseMapper
    }
}