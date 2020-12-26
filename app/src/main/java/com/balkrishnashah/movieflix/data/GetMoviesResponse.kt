package com.balkrishnashah.movieflix.data

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val Movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
){
}