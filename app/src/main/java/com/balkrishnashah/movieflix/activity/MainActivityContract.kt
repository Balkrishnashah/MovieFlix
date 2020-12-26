package com.balkrishnashah.movieflix.activity

import android.content.Context
import com.balkrishnashah.movieflix.data.Movie
import java.util.*

class MainActivityContract {

    interface view{
        fun onError()
        fun getPopularMovies()
        fun getTopRatedMovies()
        fun getUpcomingMovies()
        fun onPopularMoviesFetched(movies: List<Movie>)
        fun onTopRatedMoviesFetched(movies: List<Movie>)
        fun onUpcomingMoviesFetched(movies: List<Movie>)
        fun attachPopularMoviesOnScrollListener()
        fun attachTopRatedMoviesOnScrollListener()
        fun attachUpcomingMoviesOnScrollListener()

    }

    interface movieData{

        fun getPopularMovies(
            page: Int = 1,
            onSuccess: (Movies: List<Movie>) -> Unit,
            onError: () -> Unit
        )

        fun getTopRatedMovies(
            page: Int = 1,
            onSuccess: (movies: List<Movie>) -> Unit,
            onError: () -> Unit
        )

        fun getUpcomingMovies(
            page: Int = 1,
            onSuccess: (movies: List<Movie>) -> Unit,
            onError: () -> Unit
        )
    }
}