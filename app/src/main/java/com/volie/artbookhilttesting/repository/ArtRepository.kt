package com.volie.artbookhilttesting.repository

import androidx.lifecycle.LiveData
import com.volie.artbookhilttesting.api.RetrofitApi
import com.volie.artbookhilttesting.db.ArtDao
import com.volie.artbookhilttesting.model.Art
import com.volie.artbookhilttesting.model.ImageResponce
import com.volie.artbookhilttesting.util.Resource
import javax.inject.Inject

// we will use this repository because we want to inject our dao and retrofit api
class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi
) : ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArt()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponce> {
        return try {
            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            } else {
                Resource.error("Error!", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }

}