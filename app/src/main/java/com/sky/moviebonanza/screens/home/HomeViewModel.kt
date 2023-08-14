package com.sky.moviebonanza.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.moviebonanza.data.DataOrException
import com.sky.moviebonanza.data.Resource
import com.sky.moviebonanza.model.MovieItem
import com.sky.moviebonanza.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    /* val dataOrExceptionMovies: MutableState<DataOrException<List<MovieItem>, Boolean, String>> =
         mutableStateOf(DataOrException(null, true, ""))*/

    var listOfMovies: List<MovieItem> by mutableStateOf(listOf())
    var _listOfMovies: List<MovieItem> by mutableStateOf(listOf())
    var exception: String by  mutableStateOf("")
    var loading: Boolean by mutableStateOf(true)

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            val response = repository.getMovieList()
            println("VM SUCCESS $response")

            if (response.message.isNullOrEmpty()) {
                _listOfMovies = response.data!!
                println("VM SUCCESS _listOfMovies $_listOfMovies")
                if (_listOfMovies.isNotEmpty()) {
                    setListOfMovies()
                }else{
                    exception ="Data is Empty"
                }
                loading = false
            }else{
                exception = response.message.toString()
                loading = false
                println("VM SUCCESS exception $exception")
            }
        }
    }


    /* private fun getMovieList() {
        viewModelScope.launch {
            val response = repository.getMovieList()
            println("VM SUCCESS")
            dataOrExceptionMovies.value = response
            if (!response.data.isNullOrEmpty()) {
                setListOfMovies()
            }
        }
    }
*/
    private fun setListOfMovies() {
        listOfMovies = _listOfMovies
        println("VM setListOfMovies $listOfMovies")
    }

    fun getFilteredMovieList(query: String){
        viewModelScope.launch {
            if (query.isEmpty()) {
                setListOfMovies()
            } else {
                println("VM before Size ${listOfMovies.size}")
                listOfMovies = _listOfMovies.filter { movieItem ->
                    movieItem.Genre.contains(
                        query,
                        ignoreCase = true
                    ) || movieItem.Title.contains(
                        query,
                        ignoreCase = true
                    )
                }
                println("VM After Size ${listOfMovies.size}")
            }
        }
    }
}