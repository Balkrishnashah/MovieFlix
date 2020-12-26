package com.balkrishnashah.movieflix.api


import com.balkrishnashah.movieflix.data.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "af2a8bb4063800e504e77808e4a12bb9",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "af2a8bb4063800e504e77808e4a12bb9",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "af2a8bb4063800e504e77808e4a12bb9",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

}

