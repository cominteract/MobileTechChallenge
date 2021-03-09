package com.ainsigne.mobiletechchallenge.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ainsigne.mobiletechchallenge.model.Search

/**
 * [MovieDao] Dao for caching movie list
 */
@Dao
interface MovieDao {

    /**
     * Get all movies from search results cached
     * @return [LiveData] of [List] of [Search]
     */
    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Search>>

    /**
     * Cache movies from a search results
     * @param movies as [LiveData] of [List] of [Search] to be added to cache
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createMovies(movies : List<Search>)

}