package com.ainsigne.mobiletechchallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import com.ainsigne.mobiletechchallenge.model.MovieRecent
import com.ainsigne.mobiletechchallenge.model.Search

/**
 * [AppDatabase] The Room database for this app
 */
@Database(entities = [Search::class, MovieDBResult::class, MovieDetailsDB::class, MovieRecent::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Dao for caching movie details
     */
    abstract val movieDetailsDao : MovieDetailsDao

    /**
     * Dao for caching movie results and recents
     */
    abstract val movieResultsDao : MovieResultsDao

    /**
     * Dao for caching movie list
     */
    abstract val movieDao : MovieDao

}
