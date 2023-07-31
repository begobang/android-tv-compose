package com.nttdata.androidtv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAndroidTvAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AndroidTvAppState = remember(navController, coroutineScope){
    AndroidTvAppState(navController, coroutineScope)
}

class AndroidTvAppState(
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    //TODO: Drawer

    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

}