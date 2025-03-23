package com.example.utube.di

import android.content.Context
import androidx.room.Room
import com.example.utube.core.presentation.util.Constants
import com.example.utube.utubeapp.data.database.FavoriteVideoDatabase
import com.example.utube.utubeapp.data.database.FavoriteVideoDatabase.Companion.DB_NAME
import com.example.utube.utubeapp.data.networking.YtApi
import com.example.utube.utubeapp.data.repository.YtRepositoryImpl
import com.example.utube.utubeapp.domain.repository.YtRepository
import com.google.firebase.auth.FirebaseAuth
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

    @Provides
    @Singleton
    fun provideFavoriteVideoDatabase(@ApplicationContext context: Context): FavoriteVideoDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteVideoDatabase::class.java,
            "video.db"
        ).build()
    }
    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providePaprikaApi(): YtApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YtApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: YtApi, db: FavoriteVideoDatabase ): YtRepository {
        return YtRepositoryImpl(api, db.favoriteVideoDao)
    }
}