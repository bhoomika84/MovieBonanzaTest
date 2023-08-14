package com.sky.moviebonanza

import com.sky.moviebonanza.model.MovieItem
import com.sky.moviebonanza.network.MovieApi
import com.sky.moviebonanza.repository.MovieRepository
import com.sky.moviebonanza.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomeViewModelUnitTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Mock
    lateinit var movieAPI: MovieApi

    private lateinit var viewModel: HomeViewModel

    private val movieList = arrayListOf(
        MovieItem(Title="Parasite",Genre="Drama",Poster="https://picsum.photos/id/1/200/300"),
        MovieItem(Title="Knives Out",Genre="Crime",Poster="https://picsum.photos/id/1/200/300")
    )
    private val emptyList: ArrayList<MovieItem> = arrayListOf()
    @Test
    fun testGetMovies_EmptyList() = runTest{

        getViewModelObject(emptyList)

        Assert.assertEquals(0, viewModel.listOfMovies.size)
        Assert.assertEquals(0, viewModel._listOfMovies.size)
        Assert.assertEquals(false, viewModel.loading)
        Assert.assertEquals("Data is Empty", viewModel.exception)
    }

    @Test
    fun testGetMovies_ExpectedList() = runTest{

        getViewModelObject(movieList)

        Assert.assertEquals(2, viewModel.listOfMovies.size)
        Assert.assertEquals(2, viewModel._listOfMovies.size)
        Assert.assertEquals(false, viewModel.loading)
        Assert.assertEquals("", viewModel.exception)
    }

    @Test
    fun testGetMovies_ExpectedFilteredList() = runTest{

        getViewModelObject(movieList)

        viewModel.getFilteredMovieList("crime")
        Assert.assertEquals(1, viewModel.listOfMovies.size)
        Assert.assertEquals(2, viewModel._listOfMovies.size)
        Assert.assertEquals(false, viewModel.loading)
        Assert.assertEquals("", viewModel.exception)
        Assert.assertEquals("Knives Out", viewModel.listOfMovies[0].Title)
        Assert.assertEquals("Parasite", viewModel._listOfMovies[0].Title)
    }

    @Test
    fun testGetMovies_EmptyFilteredList() = runTest{

        getViewModelObject(movieList)

        viewModel.getFilteredMovieList("action")
        Assert.assertEquals(0, viewModel.listOfMovies.size)
        Assert.assertEquals(2, viewModel._listOfMovies.size)
        Assert.assertEquals(false, viewModel.loading)
        Assert.assertEquals("", viewModel.exception)
        Assert.assertEquals("Parasite", viewModel._listOfMovies[0].Title)
    }

    private fun getViewModelObject(listOfMovies: ArrayList<MovieItem>)  = runTest{
        Mockito.`when`(movieAPI.getAllMovies()).thenReturn(listOfMovies)
        val sut = MovieRepository(movieAPI)
        viewModel = HomeViewModel(sut)
    }
}