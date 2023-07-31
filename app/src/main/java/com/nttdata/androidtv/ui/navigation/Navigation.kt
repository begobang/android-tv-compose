package com.nttdata.androidtv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nttdata.androidtv.ui.screens.detail.MovieDetail
import com.nttdata.androidtv.ui.screens.detail.SeriesDetail
import com.nttdata.androidtv.ui.screens.home.Home
import com.nttdata.androidtv.ui.screens.similar.Similar

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screens.HOME.route){
        navigation(
            startDestination = NavCommand.ContentType(Screens.HOME).route,
            route = Screens.HOME.route
        ) {
            composable(NavCommand.ContentType(Screens.HOME)){
                Home()
            }

            composable(NavCommand.ContentTypeDetail(Screens.HOME)) {
                it.arguments?.apply {
                    getString("type")?.let { type ->
                        getString("itemId")?.let { itemId ->
                            if(type == "movie")
                                MovieDetail(itemId = itemId)
                            else
                                SeriesDetail(itemId = itemId)
                        }

                    }

                }
            }

            composable(NavCommand.ContentTypeSimilar(Screens.SIMILAR)){
                it.arguments?.apply {
                    getString("type")?.let { type ->
                        getString("itemId")?.let { itemId ->
                            Similar(itemId = itemId.toInt())
                        }

                    }

                }
            }

            composable(NavCommand.ContentTypeDetail(Screens.SIMILAR)){
                it.arguments?.apply {
                    getString("type")?.let { type ->
                        getString("itemId")?.let { itemId ->
                            if(type == "movie")
                                MovieDetail(itemId = itemId)
                            else
                                SeriesDetail(itemId = itemId)
                        }

                    }

                }
            }
        }
    }
}

private fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = navCommand.route, arguments = navCommand.args){
        content(it)
    }
}