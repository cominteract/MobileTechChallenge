package com.ainsigne.mobiletechchallenge.api;
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import retrofit2.Response
import retrofit2.http.*

/**
 * [OmdbService] Service for the api calls on the OMDBAPI
 */
interface OmdbService {

    /**
     * Fetch search results for movies based on a query
     * @param s as String the search query to filter the movies to be included
     * @param page as String the page to indicate which result group for the search query to be included
     * @param apikey as String the api key to authorize api calls
     */
    @GET(".")
    suspend fun searchMovieDBByTitleWithPage(
        @Query("s") s: String,
        @Query("page") page: String, @Query("type") type: String, @Query("apikey") apikey: String
    ) : Response<MovieDBResult>

    /**
     * Fetch the movie details by its imdb id
     * @param i as String the id to identify which details to display for the movie
     * @param apikey as String the api key to authorize api calls
     * @param plot as String the flag to identify whether to show full plot or not
     */
    @GET(".")
    suspend fun getMovieDetailDBById(
        @Query("i") i: String, @Query("apikey") apikey: String, @Query("plot") plot: String
    ) : Response<MovieDetailsDB>
}