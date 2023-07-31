package com.nttdata.androidtv

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import com.nttdata.androidtv.ui.navigation.Navigation
import com.nttdata.androidtv.ui.theme.AndroidTvTheme

@Composable
fun AndroidTvApp(appState: AndroidTvAppState = rememberAndroidTvAppState(), viewModel: AndroidTvAppViewModel = hiltViewModel()) {

    viewModel.setNavController(appState.navController)

    AndroidTvScreen {
        Navigation(navController = appState.navController)
    }
}

@Composable
fun AndroidTvScreen(content: @Composable () -> Unit){
    AndroidTvTheme {
        Box(modifier = Modifier.fillMaxSize()){
           content()
        }

    }
}