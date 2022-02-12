package com.example.photosapp.overview

import androidx.lifecycle.*
import com.example.data.database.model.PhotoDatabaseMapper
import com.example.data.network.model.PhotoApi
import com.example.data.network.model.PhotoApiMapper
import com.example.domain.ApiStatus
import com.example.domain.Photo
import com.example.usecases.photo.getphotos.GetPhotosApiUseCaseImpl
import com.example.usecases.photo.getphotos.GetPhotosDatabaseUseCaseImpl
import com.example.usecases.photo.insertphotos.InsertPhotosUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    val getPhotosApi: GetPhotosApiUseCaseImpl,
    val getPhotosDatabase: GetPhotosDatabaseUseCaseImpl,
    val insertPhotos: InsertPhotosUseCaseImpl,
    val apiMapper: PhotoApiMapper,
    val databaseMapper: PhotoDatabaseMapper
) : ViewModel() {

    val statusObservable =  BehaviorSubject.create<ApiStatus>()

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private val _navigateToSelectedPhoto = MutableLiveData<PhotoApi?>()
    val navigateToSelectedPhoto: LiveData<PhotoApi?>
        get() = _navigateToSelectedPhoto

    fun updatePhotos(newPhotos: List<Photo>) {
        _photos.value = newPhotos
    }

    fun displayPropertyDetails(photo: Photo) {
        _navigateToSelectedPhoto.value = apiMapper.toEntity(photo)
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedPhoto.value = null
    }
}
