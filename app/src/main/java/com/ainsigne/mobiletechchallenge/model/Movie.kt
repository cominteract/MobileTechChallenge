package com.ainsigne.mobiletechchallenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * movie recents model for caching every query made
 */
@Entity(tableName = "movie_recents")
data class MovieRecent (
        /**
         * id as [String] the query and page id to identify the searched query
         */
        @PrimaryKey @ColumnInfo(name = "queryPage")
        var id : String,
        /**
         * searched as [String] the searched query
         */
        var searched : String
)

/**
 * movie results model for caching all results from every query made
 */
@Entity(tableName = "movie_results")
data class MovieDBResult (
        /**
         * id as [String] the query and page id to identify the searched query
         */
        @PrimaryKey @ColumnInfo(name = "queryPage")
        var id : String,
        /**
         * Search as [List] of [Search] the movie list from the the results after query
         */
        var Search: List<Search>,
        /**
         * totalResults as [String] number of results for the query
         */
        var totalResults : String,
        /**
         * Response as [Boolean] whether the response is valid
         */
        val Response : Boolean
)

/**
 * movie searched for caching all movies from every result after query
 */
@Entity(tableName = "movies")
data class Search(
        /**
         * id as [Int] auto generated ids since we might be returning a different item with the same imdb id
         */
        @PrimaryKey(autoGenerate = true)
        var id : Int,
        /**
         * Poster as [String] the poster url for the movie
         */
        val Poster: String,
        /**
         * Title as [String] the title for the movie
         */
        val Title: String,
        /**
         * Type as [String] the type of entry for the imdb
         */
        val Type: String,
        /**
         * Year as [String] the year it was released
         */
        val Year: String,
        /**
         * imdbId as [String] the imdb id to identify the movie
         */
        val imdbID: String
)

/**
 * movie details for caching all movie details from every searched result
 */
@Entity(tableName = "movie_details")
data class MovieDetailsDB (
        /**
         * Title as [String] the title for the movie
         */
        val Title: String,
        /**
         * Year as [String] the year it was released
         */
        val Year: String,
        /**
         * Runtime as [String] the runtime duration for the movie
         */
        val Runtime: String,
        /**
         * Actors as [String] the actors for the movie
         */
        val Actors : String,
        /**
         * Plot as [String] the plot for the movie
         */
        val Plot: String,
        /**
         * Language as [String] the language translations for the movie
         */
        val Language: String,
        /**
         * Poster as [String] the poster url for the movie
         */
        val Poster: String,
        /**
         * imdbId as [String] the imdb id to identify the movie
         */
        @PrimaryKey @ColumnInfo(name = "imdbId")
        val imdbID: String)



