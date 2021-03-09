package com.ainsigne.mobiletechchallenge.viewmodel

import androidx.lifecycle.*
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieRecent

/**
 * View model to observe movie results and recent movies retrieval
 */
class MovieListViewModel(private val repo: MovieListRepository) : ViewModel() {
    /**
     * Data for all movie results currently added
     */
    var mutableLiveData : MutableLiveData<List<MovieDBResult>> = MutableLiveData()
    /**
     * Data for all movie results currently added as value
     */
    var movieResults = ArrayList<MovieDBResult>()

    /**
     * Data list for all movie results currently added
     */
    var liveData = ArrayList<LiveData<MovieDBResult>>()
    /**
     * Observer list for all movie results currently added
     */
    var observers = ArrayList<Observer<MovieDBResult>>()

    /**
     * get movie recents cached
     * @param searched the query for the recents to filter
     */
    fun getMovieRecents(searched : String) : LiveData<List<MovieRecent>>{
        return repo.getRecents(searched)
    }

    /**
     * get error status
     */
    fun getError() : LiveData<Boolean>{
        return repo.getError()
    }

    /**
     * retrieves the results already cached
     * @param query as [String] the query to identify with the results
     * @param page as [String] the page to io identify with the results
     * @param owner as [LifecycleOwner] the observer owner
     */
    fun getMovieResults(query: String, page: String, previous: String, owner: LifecycleOwner)
    : MutableLiveData<List<MovieDBResult>>{
        liveData.add(repo.getMovieResults("${query}_$page"))
        observers.add(observer(query, page))
        val pageVal = Integer.valueOf(page)
        val previousPageVal = Integer.valueOf(previous)
        if(previousPageVal >= 1)
            liveData[previousPageVal - 1].removeObserver(observers[previousPageVal - 1])
        liveData[pageVal - 1].observe(owner, observers[pageVal - 1])
        return mutableLiveData
    }

    /**
     * clears the observers before making a fresh search for a new query
     */
    fun clearObservers(){
        liveData.clear()
        observers.clear()
    }

    /**
     * observer to be added for custom paging
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
     * fetch movies when data is not available yet
     * @param query as [String] the query to identify with the results
     * @param page as [String] the page to io identify with the results
     */
    fun fetchMovies(query: String, page: String){
        repo.fetchMovies(query, page)
    }
}