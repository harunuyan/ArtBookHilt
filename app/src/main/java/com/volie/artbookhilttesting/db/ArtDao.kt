package com.volie.artbookhilttesting.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.volie.artbookhilttesting.model.Art

@Dao
interface ArtDao {

    // suspend functions asynchronously run on a background thread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art: Art)

    // live data already runs on a background thread so we don't need to use suspend
    @Query("SELECT * FROM arts")
    fun observeArt(): LiveData<List<Art>>
}