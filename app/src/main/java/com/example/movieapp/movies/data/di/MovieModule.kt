package com.example.movieapp.movies.data.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.movies.data.local.MoviesDao
import com.example.movieapp.movies.data.local.MoviesDb
import com.example.movieapp.movies.data.remote.MovieApiService
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
object MovieModule {

    @Provides
    fun provideRoomDao(database: MoviesDb): MoviesDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): MoviesDb {
        return Room.databaseBuilder(
            appContext,
            MoviesDb::class.java,
            "movies_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/")
            .build()
    }

    @Provides
    fun provideRetrofitMovieApi(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}