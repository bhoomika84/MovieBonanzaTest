package com.sky.moviebonanza.repository

import android.annotation.SuppressLint
import com.sky.moviebonanza.data.DataOrException
import com.sky.moviebonanza.data.Resource
import com.sky.moviebonanza.model.MovieItem
import com.sky.moviebonanza.network.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi) {
    @SuppressLint("SuspiciousIndentation")
    suspend fun getMovieList(): Resource<List<MovieItem>> {
        return try {
            val response = api.getAllMovies()
           // if (response.isNotEmpty()) {
                println("INSIDE Success data $response")
                Resource.Success(data = response)
           /* }else{
                Resource.Error(message ="Something went wrong", data = null)
            }*/
            //Resource.Success(data = movieList)
        } catch (exception: Exception) {
            println("INSIDE Error")
            Resource.Error(message = exception.message.toString(),data = null)
        }
    }
    /*suspend fun getMovieList(): DataOrException<List<MovieItem>, Boolean, String> {
        val dataOrException = DataOrException<List<MovieItem>, Boolean, String>()
        try {
            // dataOrException.loading  =true
            val movieList = api.getAllMovies()
            if (movieList.isNotEmpty()) dataOrException.loading = false
            println("INSIDE Success")
            dataOrException.data = movieList
            //Resource.Success(data = movieList)
        } catch (exception: Exception) {
            println("INSIDE Error")
            dataOrException.exception = exception.message.toString()
            //  Resource.Error(message = exception.message.toString())
        }
        println("Return Success")
        return dataOrException
    }*/
}