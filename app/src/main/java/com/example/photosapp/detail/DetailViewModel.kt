package com.example.photosapp.detail

import androidx.lifecycle.*
import com.example.data.database.model.PhotoDatabaseMapper
import com.example.domain.ApiStatus
import com.example.domain.Photo
import com.example.usecases.photo.getphotos.GetPhotosDatabaseUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import io.reactivex.subjects.BehaviorSubject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getPhotosDatabase: GetPhotosDatabaseUseCaseImpl,
    val databaseMapper: PhotoDatabaseMapper
) : ViewModel() {
    val statusObservable =  BehaviorSubject.create<ApiStatus>()
    var currentPhotoPosition = 0

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    fun updatePhotos(newPhotos: List<Photo>) {
        _photos.value = newPhotos
    }
}