package com.ainsigne.mobiletechchallenge.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.ainsigne.mobiletechchallenge.api.OmdbService
import com.ainsigne.mobiletechchallenge.data.MovieDetailsDao
import com.ainsigne.mobiletechchallenge.interfaces.DetailsRepository
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import com.ainsigne.mobiletechchallenge.utils.movieDbApiKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository to retrieve details from the movie navigated
 */
class MovieDetailsRepository(private val movieDetailsDao: MovieDetailsDao, private val service: OmdbService) : DetailsRepository {

    /**
     * get movie details based on its imdb id already cached
     * @param id as [String] the imdb id to identify the details to retrieve
     */
    override fun getMovieDetails(id: String) : LiveData<MovieDetailsDB> {
       return movieDetailsDao.getMovieDetailsFromId(id)
    }

    /**
     * fetch movie details base on its imdb id
     * @param id as [String] the imdb id to identify the details to retrieve
     */
    override fun fetchMovieDetails(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var response = service.getMovieDetailDBById(id, movieDbApiKey, "full")
            if(response.isSuccessful){
                response.body()?.let {
                    createMovieDetails(it)
                }
            }else{
                Log.d(" Movies ", " Movie Details Fetching Failed" + response.message().toString())
            }
        }
    }

    /**
     * creates movie details to cache locally
     * @param movieDetailsDB as [MovieDetailsDB] to be cached
     */
    override fun createMovieDetails(movieDetailsDB: MovieDetailsDB) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = movieDetailsDao.createMovieDetails(movieDetailsDB)
            Log.d(" Movies ", " Movie Details Created $id")
        }
    }
}