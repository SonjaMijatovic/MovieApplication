package com.example.movieapp.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(LocalMovieProductionCompanyConverter::class)
@Database(
    entities = [LocalMovie::class],
    version = 9,
    exportSchema = false
)
abstract class MoviesDb : RoomDatabase() {
    abstract val dao: MoviesDao
}