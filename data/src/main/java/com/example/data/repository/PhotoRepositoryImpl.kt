package com.example.data.repository

import com.example.data.database.PhotoDao
import com.example.data.database.model.PhotoDatabase
import com.example.data.database.model.PhotoDatabaseMapper
import com.example.data.network.ApiService
import com.example.data.network.model.PhotoApi
import com.example.domain.Photo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
    private val apiService: ApiService,
    private val databaseMapper: PhotoDatabaseMapper,
) : PhotoRepository {

    override fun getPhotosFromApi(): Observable<List<PhotoApi>> = apiService.getPhotos(0, 100)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getPhotosFromDatabase(): Observable<List<PhotoDatabase>> = photoDao.getAllPhotos()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun insertPhotos(photos: List<Photo>) = photoDao.insertAll(databaseMapper.toEntityList(photos))
}
