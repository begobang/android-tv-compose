package com.nttdata.androidtv.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.nttdata.androidtv.R

enum class NavigationItem(
    val navCommand: NavCommand,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    HOME(NavCommand.ContentType(Screens.HOME), R.string.home, Icons.Default.Home),
    SIMILAR(NavCommand.ContentType(Screens.SIMILAR), R.string.similar, Icons.Default.FeaturedPlayList)
}

sealed class NavCommand(
    internal val screens: Screens,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArgs> = emptyList()
) {
    class ContentType(screens: Screens): NavCommand(screens = screens)

    class ContentTypeSimilar(screens: Screens) : NavCommand(
        screens = screens,
        subRoute = "similar",
        navArgs = listOf(NavArgs.ItemId, NavArgs.Type)
    ) {
        fun createRoute(params: List<String>) =
            "${screens.route}/$subRoute/${params.joinToString("/")}"
    }

    class ContentTypeDetail(screens: Screens) : NavCommand(
        screens = screens,
        subRoute = "detail",
        navArgs = listOf(NavArgs.ItemId, NavArgs.Type)
    ) {
        fun createRoute(params: List<String>) =
            "${screens.route}/$subRoute/${params.joinToString("/")}"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(screens.route)
            .plus(subRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }
}

enum class NavArgs(val key: String, val navType: NavType<*>){
    ItemId("itemId", NavType.StringType),
    Type("type", NavType.StringType)
}