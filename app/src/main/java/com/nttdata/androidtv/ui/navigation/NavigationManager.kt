package com.nttdata.androidtv.ui.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    private var commands = MutableStateFlow<NavCommand>(NavCommand.ContentType(Screens.HOME))

    var navController: NavController? = null

    fun navigate(
        screens: Screens
    ) {
        commands.value = NavCommand.ContentType(screens)
        navController?.navigate((commands.value as NavCommand.ContentType).route)
    }

    fun navigateDetail(
        screens: Screens,
        itemId: String,
        type: String
    ){
        commands.value = NavCommand.ContentTypeDetail(screens)
        navController?.navigate(
            (commands.value as NavCommand.ContentTypeDetail).createRoute(
                listOf(
                    itemId,
                    type
                )
            )
        )
    }

    fun navigateSimilar(
        screens: Screens,
        itemId: String,
        type: String
    ){
        commands.value = NavCommand.ContentTypeSimilar(screens)
        navController?.navigate(
            (commands.value as NavCommand.ContentTypeSimilar).createRoute(
                listOf(
                    itemId,
                    type
                )
            )
        )
    }
}