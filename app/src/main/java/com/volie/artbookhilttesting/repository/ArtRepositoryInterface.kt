package com.volie.artbookhilttesting.repository

import androidx.lifecycle.LiveData
import com.volie.artbookhilttesting.model.Art
import com.volie.artbookhilttesting.model.ImageResponce
import com.volie.artbookhilttesting.util.Resource

// we will use this interface in our test cases so we can test our view model
interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponce>
}