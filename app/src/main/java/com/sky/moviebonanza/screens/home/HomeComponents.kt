package com.sky.moviebonanza.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.sky.moviebonanza.model.MovieItem
import com.sky.moviebonanza.utils.formatJSONtoString

@Composable
fun MovieRowData(movieItem: MovieItem, onItemClicked: (String) -> Unit = {}) {
    val imageUrl = movieItem.Poster
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { onItemClicked.invoke(formatJSONtoString(movieItem)) }

    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                    .build()
            )
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = "Movie Poster Image",
                modifier = Modifier.height(160.dp)

            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    is AsyncImagePainter.State.Error -> {
                        Image(
                            imageVector = Icons.Default.Movie,
                            contentDescription = "Error Image",
                        )
                    }

                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .padding(top = 4.dp, bottom = 4.dp)
            )
            Text(text = movieItem.Genre, style = MaterialTheme.typography.titleSmall)
        }
    }
}