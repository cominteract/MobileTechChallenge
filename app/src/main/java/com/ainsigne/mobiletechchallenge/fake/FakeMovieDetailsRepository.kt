package com.ainsigne.mobiletechchallenge.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ainsigne.mobiletechchallenge.interfaces.DetailsRepository
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Repository to retrieve fake details from the movie navigated
 */
class FakeMovieDetailsRepository() : DetailsRepository {

    /**
     * fake response to be added initially
     */
    var response = "{\"Title\":\"The Avengers\",\"Year\":\"2012\",\"Rated\":\"PG-13\",\"Released\":\"04 May 2012\",\"Runtime\":\"143 min\",\"Genre\":\"Action, Adventure, Sci-Fi\",\"Director\":\"Joss Whedon\",\"Writer\":\"Joss Whedon (screenplay), Zak Penn (story), Joss Whedon (story)\",\"Actors\":\"Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth\",\"Plot\":\"Nick Fury is the director of S.H.I.E.L.D., an international peace-keeping agency. The agency is a who's who of Marvel Super Heroes, with Iron Man, The Incredible Hulk, Thor, Captain America, Hawkeye and Black Widow. When global security is threatened by Loki and his cohorts, Nick Fury and his team will need all their powers to save the world from disaster.\",\"Language\":\"English, Russian, Hindi\",\"Country\":\"USA\",\"Awards\":\"Nominated for 1 Oscar. Another 38 wins & 79 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.0/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"91%\"},{\"Source\":\"Metacritic\",\"Value\":\"69/100\"}],\"Metascore\":\"69\",\"imdbRating\":\"8.0\",\"imdbVotes\":\"1,263,208\",\"imdbID\":\"tt0848228\",\"Type\":\"movie\",\"DVD\":\"22 Nov 2015\",\"BoxOffice\":\"\$623,357,910\",\"Production\":\"Marvel Studios\",\"Website\":\"N/A\",\"Response\":\"True\"}"
    /**
     * fake response to be added through fetch
     */
    var responseToBeAdded = "{\"Title\":\"Avengers: Infinity War\",\"Year\":\"2018\",\"Rated\":\"PG-13\",\"Released\":\"27 Apr 2018\",\"Runtime\":\"149 min\",\"Genre\":\"Action, Adventure, Sci-Fi\",\"Director\":\"Anthony Russo, Joe Russo\",\"Writer\":\"Christopher Markus (screenplay by), Stephen McFeely (screenplay by), Stan Lee (based on the Marvel comics by), Jack Kirby (based on the Marvel comics by), Joe Simon (Captain America created by), Jack Kirby (Captain America created by), Steve Englehart (Star-Lord created by), Steve Gan (Star-Lord created by), Bill Mantlo (Rocket Raccoon created by), Keith Giffen (Rocket Raccoon created by), Jim Starlin (Thanos,  Gamora and Drax created by), Stan Lee (Groot created by), Larry Lieber (Groot created by), Jack Kirby (Groot created by), Steve Englehart (Mantis created by), Don Heck (Mantis created by)\",\"Actors\":\"Robert Downey Jr., Chris Hemsworth, Mark Ruffalo, Chris Evans\",\"Plot\":\"The Avengers and their allies must be willing to sacrifice all in an attempt to defeat the powerful Thanos before his blitz of devastation and ruin puts an end to the universe.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Nominated for 1 Oscar. Another 46 wins & 73 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.4/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"85%\"},{\"Source\":\"Metacritic\",\"Value\":\"68/100\"}],\"Metascore\":\"68\",\"imdbRating\":\"8.4\",\"imdbVotes\":\"839,788\",\"imdbID\":\"tt4154756\",\"Type\":\"movie\",\"DVD\":\"31 Jul 2018\",\"BoxOffice\":\"\$678,815,482\",\"Production\":\"Marvel Studios\",\"Website\":\"N/A\",\"Response\":\"True\"}"

    /**
     * fake movie details to contain the data initialized from movie details response
     */
    lateinit var movieDetails : MovieDetailsDB
    /**
     * fake movie details livedata to contain the data initialized from movie details response
     */
    lateinit var liveMovieDetails : LiveData<MovieDetailsDB>

    /**
     * initialize movie details response
     */
    init {
        val detailType: Type = object : TypeToken<MovieDetailsDB>() {}.type
        movieDetails = Gson().fromJson(response, detailType)
        liveMovieDetails = MutableLiveData<MovieDetailsDB>().apply {
            this.value = movieDetails
        }

    }

    /**
     * get movie details based on its imdb id already cached
     * @param id as [String] the imdb id to identify the details to retrieve
     */
    override fun getMovieDetails(id: String) : LiveData<MovieDetailsDB> {
        return liveMovieDetails
    }

    /**
     * fetch movie details base on its imdb id
     * @param id as [String] the imdb id to identify the details to retrieve
     */
    override fun fetchMovieDetails(id: String) {
        val detailType: Type = object : TypeToken<MovieDetailsDB>() {}.type
        movieDetails = Gson().fromJson(responseToBeAdded, detailType)
        liveMovieDetails = MutableLiveData<MovieDetailsDB>().apply {
            this.value = movieDetails
        }
    }

    /**
     * creates movie details to cache locally
     * @param movieDetailsDB as [MovieDetailsDB] to be cached
     */
    override fun createMovieDetails(movieDetailsDB: MovieDetailsDB) {
        TODO("Not yet implemented")
    }
}