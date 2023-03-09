package com.volie.artbookhilttesting.di

import android.content.Context
import androidx.room.Room
import com.volie.artbookhilttesting.api.RetrofitApi
import com.volie.artbookhilttesting.db.ArtDatabase
import com.volie.artbookhilttesting.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ArtDatabase::class.java,
        "art_db"
    ).build()

    @Singleton
    @Provides
    fun injextDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi(): RetrofitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }
}