package com.example.movieapp.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<LocalMovie>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): LocalMovie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurants: List<LocalMovie>)

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    suspend fun getAllFavoriteMovies(): List<LocalMovie>

    @Update(entity = LocalMovie::class)
    suspend fun update(partialMovie: BaseLocalMovie)

    @Update(entity = LocalMovie::class)
    suspend fun updateAll(partialMovie: List<BaseLocalMovie>)

    @Update(entity = LocalMovie::class)
    suspend fun updateWithDetails(partialMovie: LocalMovieDetails)
}