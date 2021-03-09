package com.ainsigne.mobiletechchallenge.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ainsigne.mobiletechchallenge.data.AppDatabase
import com.ainsigne.mobiletechchallenge.data.MovieDao
import com.ainsigne.mobiletechchallenge.data.MovieDetailsDao
import com.ainsigne.mobiletechchallenge.data.MovieResultsDao
import com.ainsigne.mobiletechchallenge.utils.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Database module for creating instances of daos and database
 */
val databaseModule = module {
    single { createDatabase(androidContext()) }
    single { createMovieDao(get()) }
    single { createMovieDetailsDao(get()) }
    single { createMovieResultsDao(get()) }
}

/**
 * Create database instance from context
 * @param appContext as [Context] needed to initialize room database
 * @return as [AppDatabase] initialized room database
 */
private fun createDatabase(appContext: Context) : AppDatabase {
    return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        })
        .build()
}

/**
 * Create movie dao instance from room database
 * @param [AppDatabase] initialized room database
 * @return [MovieDao] movie dao instance
 */
private fun createMovieDao(database: AppDatabase) : MovieDao{
    return database.movieDao
}

/**
 * Create movie details dao instance from room database
 * @param [AppDatabase] initialized room database
 * @return [MovieDetailsDao] movie details dao instance
 */
private fun createMovieDetailsDao(database: AppDatabase) : MovieDetailsDao{
    return database.movieDetailsDao
}

/**
 * Create movie results dao instance from room database
 * @param [AppDatabase] initialized room database
 * @return [MovieResultsDao] movie results dao instance
 */
private fun createMovieResultsDao(database: AppDatabase) : MovieResultsDao{
    return database.movieResultsDao
}