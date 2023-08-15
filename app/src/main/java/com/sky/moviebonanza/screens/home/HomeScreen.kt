package com.sky.moviebonanza.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sky.moviebonanza.R
import com.sky.moviebonanza.components.MovieAppBar
import com.sky.moviebonanza.model.MovieItem
import com.sky.moviebonanza.navigation.MovieBonanzaScreens
import com.sky.moviebonanza.utils.formatStringToJSON

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        MovieAppBar(
            title = stringResource(id = R.string.app_name),
            navController = navController
        )
    })
    {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            HomeContent(navController, viewModel, it)
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController,
    viewModel: HomeViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadContentForList(navController, viewModel)
    }
}

@Composable
fun showLoading() {
    Column(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun LoadContentForList(navController: NavController, viewModel: HomeViewModel) {

    println("OUTSIDE LoadContentForList ${viewModel.loading} ${viewModel.listOfMovies}")
    if (viewModel.loading) {
        showLoading()
    } else if (viewModel.exception.isNotEmpty()) {
        println("OUTSIDE Exception ${viewModel.exception} || ")
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = viewModel.exception)
        }
    } else {
        println("OUTSIDE Data")
        ShowMovieListContent(navController, viewModel)
    }
}

@Composable
fun ShowMovieListContent(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchMovie { query ->
            println("Search Screen $query")
            viewModel.getFilteredMovieList(query)
        }
        ShowMovieListData(navController, viewModel)
    }
}


@Composable
fun ShowMovieListData(navController: NavController, viewModel: HomeViewModel) {
    val movieList: List<MovieItem> = viewModel.listOfMovies
    if (movieList.isEmpty()) {
        Text(text = stringResource(id = R.string.search_empty))
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 158.dp),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(movieList) {
                MovieRowData(it) {movieItem ->
                    val objMovie = formatStringToJSON(movieItem)
                    navController.navigate(MovieBonanzaScreens.DetailsScreen.name+"/${objMovie}")
                }
            }
        }
    }
}


