package com.sky.moviebonanza.repository

import android.annotation.SuppressLint
import com.sky.moviebonanza.data.Resource
import com.sky.moviebonanza.model.MovieItem
import com.sky.moviebonanza.network.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi) {
    @SuppressLint("SuspiciousIndentation")
    suspend fun getMovieList(): Resource<List<MovieItem>> {
        return try {
            val response = api.getAllMovies()
            Resource.Success(data = response)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString(), data = null)
        }
    }
}