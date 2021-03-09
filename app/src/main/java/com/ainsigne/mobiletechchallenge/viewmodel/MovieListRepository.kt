package com.ainsigne.mobiletechchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ainsigne.mobiletechchallenge.api.OmdbService
import com.ainsigne.mobiletechchallenge.data.MovieDao
import com.ainsigne.mobiletechchallenge.data.MovieResultsDao
import com.ainsigne.mobiletechchallenge.interfaces.MoviesRepository
import com.ainsigne.mobiletechchallenge.model.*
import com.ainsigne.mobiletechchallenge.utils.movieDbApiKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * Repository to retrieve search results from the query inputted
 * @param movieDao as [MovieDao]
 * @param movieResultsDao as [MovieResultsDao]
 * @param service as [OmdbService]
 */
class MovieListRepository(var movieDao: MovieDao, var movieResultsDao: MovieResultsDao,
                          private val service: OmdbService) : MoviesRepository {

    private val errorData : MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        this.value = false
    }
    /**
     * retrieves the recents already cached
     * @param searched as [String] to indicate which text the recently searched contains
     */
    override fun getRecents(searched: String): LiveData<List<MovieRecent>> {
        return movieResultsDao.getRecents("%${searched}%")
    }

    /**
     * retrieves the movies already cached from query
     * @param query as [String] the query to identify with the movies
     * @param page as [String] the page of the movies listed
     */
    override fun getMovies(query: String, page: String): LiveData<List<Search>> {
        return movieDao.getAllMovies()
    }
    /**
     * retrieves the results already cached
     * @param id as [String] the query to identify with the movies
     */
    override fun getMovieResults(id: String): LiveData<MovieDBResult> {

        return movieResultsDao.getMovieResults(id)
    }

    /**
     * creates the movie results to be cache locally
     * @param movieResults as [MovieDBResult] queried results
     */
    override fun createMovieResults(movieResults: MovieDBResult) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = movieResultsDao.createMovieResults(movieResults)

        }
    }

    /**
     * creates movie recents to be cache locally
     * @param movieRecent as [MovieRecent] queried text
     */
    override fun createMovieRecents(movieRecent: MovieRecent) {
        CoroutineScope(Dispatchers.IO).launch {
            val recentId = movieResultsDao.createMovieRecent(movieRecent)
        }
    }

    /**
     * handles fetching of movies to be updated constantly
     * @param query as [String] the query to identify with the movies
     * @param page as [String] the page of the movies listed
     */
    override fun fetchMovies(query: String, page: String) {
        errorData.value = false
        CoroutineScope(Dispatchers.IO).launch {

            var response = service.searchMovieDBByTitleWithPage(query, page, "movie", movieDbApiKey)
            if(response.isSuccessful){
                response.body()?.let {
                    if(it.Response) {
                        it.id = "${query}_${page}"
                        createMovieResults(movieResults = it)
                        var movieRecent = MovieRecent(query, query)
                        createMovieRecents(movieRecent)
                        CoroutineScope(Dispatchers.Main).launch{
                            errorData.value = false
                        }
                    }else{
                        CoroutineScope(Dispatchers.Main).launch{
                            if(Integer.valueOf(page) == 1)
                                errorData.value = true
                        }
                    }
                }
            }else{
                CoroutineScope(Dispatchers.Main).launch{
                    if(Integer.valueOf(page) == 1)
                        errorData.value = true
                }
            }
        }
    }

    /**
     * error handling for when there is no results
     */
    override fun getError(): LiveData<Boolean> {
        return errorData
    }
}