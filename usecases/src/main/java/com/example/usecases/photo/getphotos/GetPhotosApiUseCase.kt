package com.example.usecases.photo.getphotos

import com.example.data.network.model.PhotoApi
import io.reactivex.Observable

interface GetPhotosApiUseCase {
    operator fun invoke(): Observable<List<PhotoApi>>
}