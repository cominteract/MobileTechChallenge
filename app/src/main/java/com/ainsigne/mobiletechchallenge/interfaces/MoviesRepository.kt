package com.ainsigne.mobiletechchallenge.interfaces

import androidx.lifecycle.LiveData
import com.ainsigne.mobiletechchallenge.model.Search
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieRecent

/**
 * Repository to retrieve search results from the query inputted
 */
interface MoviesRepository {
    /**
     * Retrieves the movies already cached from query
     * @param query as [String] the query to identify with the movies
     * @param page as [String] the page of the movies listed
     */
    fun getMovies(query : String, page : String) : LiveData<List<Search>>


    /**
     * Retrieves the recents already cached
     * @param searched as [String] to indicate which text the recently searched contains
     */
    fun getRecents(searched : String) : LiveData<List<MovieRecent>>

    /**
     * Retrieves the results already cached
     * @param id as [String] the query to identify with the movies
     */
    fun getMovieResults(id : String) : LiveData<MovieDBResult>


    /**
     * Creates the movie results to be cache locally
     * @param movieResults as [MovieDBResult] queried results
     */
    fun createMovieResults(movieResults: MovieDBResult)

    /**
     * Creates movie recents to be cache locally
     * @param movieRecent as [MovieRecent] queried text
     */
    fun createMovieRecents(movieRecent: MovieRecent)

    /**
     * Handles fetching of movies to be updated constantly
     * @param query as [String] the query to identify with the movies
     * @param page as [String] the page of the movies listed
     */
    fun fetchMovies(query : String , page : String)

    /**
     * Error handling for when there is no results
     */
    fun getError() : LiveData<Boolean>
}