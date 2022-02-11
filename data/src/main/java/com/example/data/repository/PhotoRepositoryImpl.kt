package com.example.data.repository

import com.example.data.database.PhotoDao
import com.example.data.database.model.PhotoDatabaseMapper
import com.example.data.network.ApiService
import com.example.domain.Photo
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    val photoDao: PhotoDao,
    val apiService: ApiService,
    val databaseMapper: PhotoDatabaseMapper,
) : PhotoRepository {

    override suspend fun getPhotosFromApi() = apiService.getPhotos(0, 100)
    override fun getPhotosFromDatabase() = photoDao.getAllPhotos()
    override fun insertPhotos(photos: List<Photo>) = photoDao.insertAll(databaseMapper.toEntityList(photos))
}
