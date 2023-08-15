package com.sky.moviebonanza.network

import com.sky.moviebonanza.model.MovieItem
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface MovieApi {
    @GET("b/YNFW")
    suspend fun getAllMovies(): ArrayList<MovieItem>
}