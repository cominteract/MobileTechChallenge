package com.ainsigne.mobiletechchallenge.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ainsigne.mobiletechchallenge.interfaces.MoviesRepository
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieRecent
import com.ainsigne.mobiletechchallenge.model.Search
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Repository to retrieve fake search results from the query inputted
 */
class FakeMovieListRepository : MoviesRepository {

    /**
     * Fake data for movies searched
     */
    var movieSearch = MutableLiveData<List<Search>>()

    /**
     * Fake data for recent searched queries
     */
    var movieRecents = MutableLiveData<List<MovieRecent>>()

    /**
     * Fake data for movie results retrieved
     */
    var movieResults = MutableLiveData<MovieDBResult>()

    /**
     * Fake data for all movie results currently added
     */
    var allMovieResults = MutableLiveData<List<MovieDBResult>>()

    /**
     * Fake results being retrieved
     */
    lateinit var movieResult : MovieDBResult
    /**
     * Fake movie search list being retrieved
     */
    lateinit var searches : List<Search>
    /**
     * Fake data for all movie results currently added as value
     */
    var results = ArrayList<MovieDBResult>()
    /**
     * Fake data for recent searched queries as value
     */
    var recents = ArrayList<MovieRecent>()

    /**
     * Fake response to be added when initialized
     */
    var response = "{\"Search\":[{\"Title\":\"Star Trek\",\"Year\":\"2009\",\"imdbID\":\"tt0796366\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjE5NDQ5OTE4Ml5BMl5BanBnXkFtZTcwOTE3NDIzMw@@._V1_SX300.jpg\"},{\"Title\":\"Star Trek Into Darkness\",\"Year\":\"2013\",\"imdbID\":\"tt1408101\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTk2NzczOTgxNF5BMl5BanBnXkFtZTcwODQ5ODczOQ@@._V1_SX300.jpg\"},{\"Title\":\"Star Trek Beyond\",\"Year\":\"2016\",\"imdbID\":\"tt2660888\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BODgzMTZmMGMtNTM4Ni00ZjIxLWIyYmUtZjcxMDEwMjkxMzQyXkEyXkFqcGdeQXVyMjMwNDgzNjc@._V1_SX300.jpg\"},{\"Title\":\"Star Trek: First Contact\",\"Year\":\"1996\",\"imdbID\":\"tt0117731\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYTllZjRkY2QtYTJlMy00ZTMxLWE0YWQtMWMwYzY2YTM3YzRjXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"Star Trek II: The Wrath of Khan\",\"Year\":\"1982\",\"imdbID\":\"tt0084726\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMzcyYWE5YmQtNDE1Yi00ZjlmLWFlZTAtMzRjODBiYjM3OTA3XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\"},{\"Title\":\"Star Trek: The Next Generation\",\"Year\":\"1987–1994\",\"imdbID\":\"tt0092455\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOWFhYjE4NzMtOWJmZi00NzEyLTg5NTctYmIxMTU1ZDIxMDAyXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg\"},{\"Title\":\"Star Trek: Discovery\",\"Year\":\"2017–\",\"imdbID\":\"tt5171438\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTUzYTY4OTctNDg3NS00ZjY1LWE5Y2YtMTBiODU5OTViOTE0XkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg\"},{\"Title\":\"Star Trek: The Motion Picture\",\"Year\":\"1979\",\"imdbID\":\"tt0079945\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzNlMzNlNmQtNmYzNS00YmU5LWIzYWQtMDRkYzIzNzEzOTIyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\"},{\"Title\":\"Star Trek IV: The Voyage Home\",\"Year\":\"1986\",\"imdbID\":\"tt0092007\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYWM4MWEwNWYtZjNhYS00ZWVhLWE2ZjAtMDAyMGRkMDk2NDBlXkEyXkFqcGdeQXVyMjA0MDQ0Mjc@._V1_SX300.jpg\"},{\"Title\":\"Star Trek: Generations\",\"Year\":\"1994\",\"imdbID\":\"tt0111280\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNjFiMzc4YzAtNGMzYS00NjI0LWJhYTYtN2JiOTI2ODczYzE3XkEyXkFqcGdeQXVyNTUyMzE4Mzg@._V1_SX300.jpg\"}],\"totalResults\":\"437\",\"Response\":\"True\"}"
    /**
     * Fake response to be added when fetched
     */
    var responseToBeAdded = "{\"Search\":[{\"Title\":\"The Avengers\",\"Year\":\"2012\",\"imdbID\":\"tt0848228\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\"},{\"Title\":\"Avengers: Infinity War\",\"Year\":\"2018\",\"imdbID\":\"tt4154756\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_SX300.jpg\"},{\"Title\":\"Avengers: Endgame\",\"Year\":\"2019\",\"imdbID\":\"tt4154796\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX300.jpg\"},{\"Title\":\"Avengers: Age of Ultron\",\"Year\":\"2015\",\"imdbID\":\"tt2395427\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTM4OGJmNWMtOTM4Ni00NTE3LTg3MDItZmQxYjc4N2JhNmUxXkEyXkFqcGdeQXVyNTgzMDMzMTg@._V1_SX300.jpg\"},{\"Title\":\"The Avengers\",\"Year\":\"1998\",\"imdbID\":\"tt0118661\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYWE1NTdjOWQtYTQ2Ny00Nzc5LWExYzMtNmRlOThmOTE2N2I4XkEyXkFqcGdeQXVyNjUwNzk3NDc@._V1_SX300.jpg\"},{\"Title\":\"The Avengers: Earth's Mightiest Heroes\",\"Year\":\"2010–2012\",\"imdbID\":\"tt1626038\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYzA4ZjVhYzctZmI0NC00ZmIxLWFmYTgtOGIxMDYxODhmMGQ2XkEyXkFqcGdeQXVyNjExODE1MDc@._V1_SX300.jpg\"},{\"Title\":\"Ultimate Avengers: The Movie\",\"Year\":\"2006\",\"imdbID\":\"tt0491703\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTYyMjk0NTMwMl5BMl5BanBnXkFtZTgwNzY0NjAwNzE@._V1_SX300.jpg\"},{\"Title\":\"Ultimate Avengers II\",\"Year\":\"2006\",\"imdbID\":\"tt0803093\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZjI3MTI5ZTYtZmNmNy00OGZmLTlhNWMtNjZiYmYzNDhlOGRkL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"The Avengers\",\"Year\":\"1961–1969\",\"imdbID\":\"tt0054518\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZWQwZTdjMDUtNTY1YS00MDI0LWFkNjYtZDA4MDdmZjdlMDRlXkEyXkFqcGdeQXVyNjUwNzk3NDc@._V1_SX300.jpg\"},{\"Title\":\"Avengers Assemble\",\"Year\":\"2013–2019\",\"imdbID\":\"tt2455546\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTY0NTUyMDQwOV5BMl5BanBnXkFtZTgwNjAwMTA0MDE@._V1_SX300.jpg\"}],\"totalResults\":\"112\",\"Response\":\"True\"}"
    /**
     * Fake query to identify the fetched response
     */
    public val queryToBeAdded = "avengers"
    /**
     * Fake page to identify the fetched response
     */
    public val pageToBeAdded = "1"

    /**
     * Initialize default results from fake response
     */
    init {


        val listType: Type = object : TypeToken<MovieDBResult>() {}.type

        movieResult = Gson().fromJson(response, listType)
        movieResult.id = "trek_1";
        searches = movieResult.Search

        movieResults.value = movieResult
        movieSearch.value =  searches
        allMovieResults.value = ArrayList()
        results.add(movieResult)
        allMovieResults.value = results

        recents.add(MovieRecent("trek_1","trek_1"))
        movieRecents.value = recents

    }

    /**
     * Retrieves the fake movies
     * @param query as [String] the query to identify with the movies
     * @param page as [String] the page of the movies listed
     */
    override fun getMovies(query: String, page: String): LiveData<List<Search>> {
        return movieSearch

    }
    /**
     * Retrieves the fake recents
     * @param searched as [String] to indicate which text the recently searched contains
     */
    override fun getRecents(searched: String): LiveData<List<MovieRecent>> {
        return movieRecents

    }
    /**
     *
     * Retrieves the fake results
     * @param id as [String] the query to identify with the movies
     */
    override fun getMovieResults(id: String): LiveData<MovieDBResult> {
        return movieResults

    }

    /**
     * Creates the fake movie results
     * @param movieResults as [MovieDBResult] queried results
     */
    override fun createMovieResults(movieResults: MovieDBResult) {
        results.add(movieResults)
        allMovieResults.value = results
    }

    /**
     * Creates fake movie recents
     * @param movieRecent as [MovieRecent] queried text
     */
    override fun createMovieRecents(movieRecent: MovieRecent) {
        recents.add(movieRecent)
        movieRecents.value = recents
    }

    /**
     * Handles fetching of movies to be updated constantly
     * @param query as [String] the query to identify with the movies
     * @param page as [String] the page of the movies listed
     */
    override fun fetchMovies(query: String, page: String) {
        val listType: Type = object : TypeToken<MovieDBResult>() {}.type
        movieResult = Gson().fromJson(responseToBeAdded, listType)
        movieResult.id = "${query}_${page}";
        searches = movieResult.Search
        movieResults.value = movieResult
        movieSearch.value =  searches
        results.add(movieResult)
        allMovieResults.value = results

    }

    /**
     * Error handling for when there is no results
     */
    override fun getError(): LiveData<Boolean> {
        return MutableLiveData<Boolean>()
    }
}