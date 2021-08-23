package com.siriusproject.coshelek.wallet_list.ui.view.navigation

import android.util.Log
import androidx.navigation.NavController
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

typealias NavigationCommand = (NavController) -> Unit

@ActivityRetainedScoped
class NavigationDispatcher @Inject constructor() {
    private val navigationEmitter: Channel<NavigationCommand> = Channel(Channel.UNLIMITED)

    @OptIn(DelicateCoroutinesApi::class)
    val navigationCommands =
        navigationEmitter.receiveAsFlow().shareIn(GlobalScope, SharingStarted.WhileSubscribed())

    fun emit(navigationCommand: NavigationCommand) {
        Log.d(javaClass.name, "Nav Command added to flow")
        navigationEmitter.trySend(navigationCommand)
    }
}