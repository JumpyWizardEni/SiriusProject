package com.siriusproject.coshelek.wallet_list.ui.view.navigation

import android.util.Log
import androidx.navigation.NavController
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

typealias NavigationCommand = (NavController) -> Unit

@ActivityRetainedScoped
class NavigationDispatcher @Inject constructor() {
    var navigationEmitter: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)

    fun emit(navigationCommand: NavigationCommand) {
        Log.d(javaClass.name, "Nav Command added to flow")
        navigationEmitter.value = navigationCommand
    }
}