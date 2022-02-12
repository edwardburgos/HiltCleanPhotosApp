package com.example.usecases.photo.getphotos

import com.example.data.database.model.PhotoDatabase
import io.reactivex.Observable

interface GetPhotosDatabaseUseCase {
    operator fun invoke(): Observable<List<PhotoDatabase>>
}