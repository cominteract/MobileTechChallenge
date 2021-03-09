package com.ainsigne.mobiletechchallenge.interfaces

import androidx.lifecycle.LiveData
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB

/**
 * Repository to retrieve details from the movie navigated
 */
interface DetailsRepository {
    /**
     * Get movie details based on its imdb id already cached
     * @param id as [String] the imdb id to identify the details to retrieve
     */
    fun getMovieDetails(id : String) : LiveData<MovieDetailsDB>

    /**
     * Fetch movie details base on its imdb id
     * @param id as [String] the imdb id to identify the details to retrieve
     */
    fun fetchMovieDetails(id : String)

    /**
     * Creates movie details to cache locally
     * @param movieDetailsDB as [MovieDetailsDB] to be cached
     */
    fun createMovieDetails(movieDetailsDB: MovieDetailsDB)
}