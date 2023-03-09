package com.volie.artbookhilttesting.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.volie.artbookhilttesting.model.Art

@Database(entities = [Art::class], version = 1, exportSchema = false)
abstract class ArtDatabase : RoomDatabase() {

    abstract fun artDao(): ArtDao

    /*@Volatile
    private var INSTANCE: ArtDatabase? = null

    fun getDatabase(contect: Context): ArtDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                contect,
                ArtDatabase::class.java,
                "art_db"
            ).build()
            INSTANCE = instance
            return instance
        }
    }*/
}