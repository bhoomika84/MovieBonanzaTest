package com.sky.moviebonanza.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky.moviebonanza.R

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
    isTrailing: Boolean = false,
    valueUpdate: (String) -> Unit = {}
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
            ClearVisibility(valueState.value, isTrailing) {
                valueState.value = ""
                valueUpdate.invoke("")
            }
        }

    )
}

@Composable
fun ClearVisibility(value: String, isTrailing: Boolean, clearText: () -> Unit = {}) {
    val visible = value.isNotEmpty() && isTrailing
    if (visible) {
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
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable() {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current

        InputField(
            valueState = searchQueryState,
            labelId = stringResource(id = R.string.search),
            enabled = true,

            onAction = KeyboardActions {
                keyboardController?.hide()
            },
            isTrailing = true,
        ) {
            if (searchQueryState.value.trim().isEmpty()) {
                keyboardController?.hide()
            }
            onSearch(searchQueryState.value.trim())
        }
    }
}
