package com.sky.moviebonanza.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sky.moviebonanza.R

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

