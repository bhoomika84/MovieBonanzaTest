package com.sky.moviebonanza.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.sky.moviebonanza.R
import com.sky.moviebonanza.model.MovieItem

@Composable
fun MovieLogo(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.app_name),
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(
    title: String,
    backIcon: ImageVector? = null,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.shadow(0.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (backIcon != null) {
                    Icon(imageVector = backIcon,
                        contentDescription = "Back Arrow",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                } else {
                    Icon(
                        imageVector = Icons.Default.Movie,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "App Icon Movie"
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
        ),
        actions = {
            IconButton(onClick = { }) {
                if (backIcon == null) {
                    Icon(
                        imageVector = Icons.Default.Info, contentDescription = "Info"
                    )
                } else Box {}
            }
        }
    )

}

@Composable
fun MovieRowData(movieItem: MovieItem) {
    val imageUrl = movieItem.Poster
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    isTrailing: Boolean =false,
    clearVisibility : MutableState<Boolean> = mutableStateOf(false),
    valueUpdate: (String) -> Unit ={}
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            valueUpdate.invoke(it)
                        },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.primary),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        trailingIcon = {
            ClearVisibility(valueState.value,isTrailing){
                valueState.value = ""
                valueUpdate.invoke("")
            }
        }

    )
}


@Composable
fun ClearVisibility(value: String, isTrailing: Boolean, clearText: () -> Unit ={}){
    val visible = value.isNotEmpty() && isTrailing
    if(visible) {
        IconButton(onClick = { clearText.invoke() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchMovie(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable() {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
       /* val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }*/
        InputField(
            valueState = searchQueryState,
            labelId = stringResource(id = R.string.search),
            enabled = true,

            onAction = KeyboardActions {
               /* if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""*/
                keyboardController?.hide()
            },
            isTrailing = true,
        ){
            if (searchQueryState.value.trim().isEmpty()){
                keyboardController?.hide()
            } //return@KeyboardActions
            onSearch(searchQueryState.value.trim())
            //searchQueryState.value = ""
            //keyboardController?.hide()
        }
    }
}

