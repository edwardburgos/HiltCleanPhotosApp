package com.example.data.repository

import com.example.data.database.model.PhotoDatabase
import com.example.data.network.model.PhotoApi
import com.example.domain.Photo
import io.reactivex.Observable

interface PhotoRepository {
    fun getPhotosFromApi(): Observable<List<PhotoApi>>
    fun getPhotosFromDatabase(): Observable<List<PhotoDatabase>>
    fun insertPhotos(photos: List<Photo>)
}