package com.nttdata.androidtv

import androidx.navigation.NavController
import com.nttdata.androidtv.di.IODispatcher
import com.nttdata.androidtv.ui.navigation.NavigationManager
import com.rocket.android.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class AndroidTvAppViewModel @Inject constructor(
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
    private val navigationManager: NavigationManager
): BaseViewModel(dispatcher = coroutineDispatcher) {

    fun setNavController(navController: NavController){
        navigationManager.navController = navController
    }
}