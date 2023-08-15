package com.sky.moviebonanza

import com.sky.moviebonanza.data.Resource
import com.sky.moviebonanza.model.MovieItem
import com.sky.moviebonanza.network.MovieApi
import com.sky.moviebonanza.repository.MovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MovieRepositoryUnitTest {

    @Mock
    lateinit var movieAPI: MovieApi

    private val movieList = arrayListOf(
        MovieItem(Title="Parasite",Genre="Drama",Poster="https://picsum.photos/id/1/200/300"),
        MovieItem(Title="Knives Out",Genre="Crime",Poster="https://picsum.photos/id/1/200/300")
    )
    private val emptyList: ArrayList<MovieItem> = arrayListOf()

    private lateinit var result: Resource<List<MovieItem>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetMovies_EmptyList(): Unit = runTest {

        getViewModelObject(emptyList)

        Assert.assertEquals(true, result is Resource.Success)
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun testGetMovies_ExpectedMovieList(): Unit = runTest {

        getViewModelObject(movieList)

        Assert.assertEquals(true, result is Resource.Success)
        Assert.assertEquals(2, result.data!!.size)
        Assert.assertEquals("Parasite", result.data!![0].Title)
    }

    private fun getViewModelObject(listOfMovies: ArrayList<MovieItem>)  = runTest{
        Mockito.`when`(movieAPI.getAllMovies()).thenReturn(listOfMovies)
        val sut = MovieRepository(movieAPI)
        result = sut.getMovieList()
    }
}