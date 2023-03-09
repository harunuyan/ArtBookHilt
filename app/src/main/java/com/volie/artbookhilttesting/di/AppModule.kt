package com.volie.artbookhilttesting.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.volie.artbookhilttesting.R
import com.volie.artbookhilttesting.api.RetrofitApi
import com.volie.artbookhilttesting.db.ArtDao
import com.volie.artbookhilttesting.db.ArtDatabase
import com.volie.artbookhilttesting.repository.ArtRepository
import com.volie.artbookhilttesting.repository.ArtRepositoryInterface
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

    @Singleton
    @Provides
    fun injectNormalRepository(
        dao: ArtDao,
        api: RetrofitApi
    ): ArtRepositoryInterface {
        return ArtRepository(dao, api)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.color.purple_200)
                .error(R.drawable.ic_launcher_background)
        )
}