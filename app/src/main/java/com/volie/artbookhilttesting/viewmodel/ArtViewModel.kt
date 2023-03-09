package com.volie.artbookhilttesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volie.artbookhilttesting.model.Art
import com.volie.artbookhilttesting.model.ImageResponce
import com.volie.artbookhilttesting.repository.ArtRepositoryInterface
import com.volie.artbookhilttesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    fun resetInsertArtMesssage() {
        _insertArtMessage = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        _selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) {
        viewModelScope.launch { repository.deleteArt(art) }
    }

    fun insertArt(art: Art) {
        viewModelScope.launch {
            repository.insertArt(art)
        }
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            _insertArtMessage.postValue(Resource.error("Please fill out all fields", null))
            return
        } else {
            val yearInt = try {
                year.toInt()
            } catch (e: Exception) {
                _insertArtMessage.postValue(Resource.error("Please enter a valid year", null))
                return
            }
            val art = Art(name, artistName, yearInt, _selectedImage.value ?: "")
            insertArt(art)
            setSelectedImage("")
            _insertArtMessage.postValue(Resource.success(art))
        }
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        } else {
            _images.value = Resource.loading(null)
            viewModelScope.launch {
                val response = repository.searchImage(searchString)
                _images.postValue(response)
            }
        }
    }
}