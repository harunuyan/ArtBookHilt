package com.volie.artbookhilttesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volie.artbookhilttesting.model.Art
import com.volie.artbookhilttesting.model.ImageResponce
import com.volie.artbookhilttesting.repository.ArtRepositoryInterface
import com.volie.artbookhilttesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    // we will use this in our ArtFragment
    val artList = repository.getArt()

    // search images from the api
    private val _images = MutableLiveData<Resource<ImageResponce>>()
    val images: LiveData<Resource<ImageResponce>> = _images

    // url of the selected image
    private val _selectedImage = MutableLiveData<String>()
    val selectedImage: LiveData<String> = _selectedImage

    // we will use this in our ArtDetailsFragment
    private var _insertArtMessage = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>> = _insertArtMessage

    // cause we are using livedata we need to reset it after we use it
    fun resetInsertArtMessage() {
        _insertArtMessage = MutableLiveData<Resource<Art>>()
    }
}