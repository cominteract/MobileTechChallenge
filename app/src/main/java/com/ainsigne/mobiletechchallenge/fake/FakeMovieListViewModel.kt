package com.ainsigne.mobiletechchallenge.fake

import androidx.lifecycle.*
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieRecent

/**
 * View model to observe movie results and recent movies retrieval
 */
class FakeMovieListViewModel(private val repo: FakeMovieListRepository) : ViewModel() {
    /**
     * Fake data for all movie results currently added
     */
    var mutableLiveData = repo.allMovieResults
    /**
     * Fake data for all movie results currently added as value
     */
    var movieResults = ArrayList<MovieDBResult>()



    /**
     * Get fake movie recents
     * @param searched the query for the recents to filter
     */
    fun getMovieRecents(searched : String) : LiveData<List<MovieRecent>>{
        return repo.getRecents(searched)
    }

    /**
     * Get error status
     */
    fun getError() : LiveData<Boolean>{
        return repo.getError()
    }

    /**
     * Retrieves the fake results
     * @param query as [String] the query to identify with the results
     * @param page as [String] the page to io identify with the results
     * TODO : unused
     */
    fun getMovieResultsList(query: String, page: String, previous: String, lifecycle: Lifecycle)
    : MutableLiveData<List<MovieDBResult>>{


        repo.getMovieResults("${query}_$page").observe( { lifecycle } , observer(query, page) )

        return mutableLiveData
    }

    /**
     *
     * Retrieves the fake results
     * @param query as [String] the query to identify with the results
     * @param page as [String] the page to io identify with the results
     */
    fun getMovieResults(query: String, page: String, previous: String)
            : LiveData<MovieDBResult>{
        return repo.getMovieResults("${query}_$page")
    }


    /**
     * Clears the observers before making a fresh search for a new query
     */
    fun clearObservers(){


    }

    /**
     * Observer to be added for custom paging
     * @param query as [String] the query to identify with the results
     * @param page as [String] the page to io identify with the results
     */
    fun observer(query: String, page: String) : Observer<MovieDBResult> {
        return Observer<MovieDBResult> {
            if(it == null){
                fetchMovies(query, page)
            }else{
                movieResults.add(it)
                mutableLiveData.postValue(movieResults)
            }
        }
    }

    /**
     * Fetch movies when data is not available yet
     * @param query as [String] the query to identify with the results
     * @param page as [String] the page to io identify with the results
     */
    fun fetchMovies(query: String, page: String){
        repo.fetchMovies(query, page)
    }
}