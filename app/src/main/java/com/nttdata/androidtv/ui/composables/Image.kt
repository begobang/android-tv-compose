package com.nttdata.androidtv.ui.composables

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import coil.compose.AsyncImage
import com.nttdata.androidtv.R

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Image(path: String?, contentDescription: String, modifier: Modifier = Modifier, contentScale: ContentScale){
    AsyncImage(
        model = stringResource(id = R.string.path, path ?: ""),
        contentDescription = contentDescription,
        modifier = modifier.background(color = MaterialTheme.colorScheme.secondary),
        contentScale = contentScale,
        placeholder = painterResource(id = R.drawable.ic_logo),
        error = painterResource(id = R.drawable.ic_logo)
    )
}