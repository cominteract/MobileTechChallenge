package com.ainsigne.mobiletechchallenge.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import com.ainsigne.mobiletechchallenge.model.MovieRecent

/**
 * [MovieResultsDao] Dao for caching movie results and recents
 */
@Dao
interface MovieResultsDao {

    /**
     * Get movie results from searched queries
     * @return [LiveData] of [MovieDBResult]
     */
    @Query("SELECT * FROM movie_results ")
    fun getMovieResults(): LiveData<MovieDBResult>

    /**
     * Get recently searched queries
     * @param searched as [String] the searched query
     * @return [LiveData] of [List] pf [MovieRecent]
     */
    @Query("SELECT * FROM movie_recents WHERE searched LIKE :searched ")
    fun getRecents(searched : String) : LiveData<List<MovieRecent>>

    /**
     * Get movie results from its id
     * @param id as [String] to identify the movie result to be returned
     * @return [LiveData] of [MovieDBResult] returned
     */
    @Query("SELECT * FROM movie_results WHERE queryPage = :id")
    fun getMovieResults(id: String): LiveData<MovieDBResult>

    /**
     * Create movie results cached from each query
     * @param movieDBResult as [LiveData] of [MovieDBResult] to be added to cache
     * @return [Long] if greater than 1 it means it succeeded
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createMovieResults(movieDBResult: MovieDBResult) : Long

    /**
     * Create movie recents cached from each query
     * @param movieRecent as [LiveData] of [MovieRecent] to be added to cache
     * @return [Long] if greater than 1 it means it succeeded
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createMovieRecent(movieRecent: MovieRecent) : Long

}