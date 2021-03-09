package com.ainsigne.mobiletechchallenge.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import com.ainsigne.mobiletechchallenge.model.Search

/**
 * [MovieDetailsDao] Dao for caching movie details
 */
@Dao
interface MovieDetailsDao {

    /**
     * Get movie details cached based on its id
     * @param id as [String] to identify the movie details to be returned
     * @return [LiveData] of [MovieDetailsDB]
     */
    @Query("SELECT * FROM movie_details WHERE imdbID = :id")
    fun getMovieDetailsFromId(id : String): LiveData<MovieDetailsDB>

    /**
     * Create movie details to be cached
     * @param movieDetailsDB as [LiveData] of [MovieDetailsDB] to be added to cache
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createMovieDetails(movieDetailsDB: MovieDetailsDB) : Long

}