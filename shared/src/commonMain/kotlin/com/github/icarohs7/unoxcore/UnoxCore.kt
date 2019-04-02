package com.github.icarohs7.unoxcore

import com.github.icarohs7.unoxcore.delegates.mutableLazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object UnoxCore {
    /**
     * Coroutine dispatcher used to execute work on
     * background
     */
    var backgroundDispatcher: CoroutineDispatcher by mutableLazy { Dispatchers.Default }

    /**
     * Coroutine dispatcher used to execute work on
     * foreground, usually on a UI Thread
     */
    var foregroundDispatcher: CoroutineDispatcher by mutableLazy { Dispatchers.Default }

    /**
     * Whether or not the library should for the context
     * switch to the background dispatcher when running on
     * another background dispatcher or not
     */
    var forceContextSwitchToBackground: Boolean by mutableLazy { false }

    /**
     * Function used to log various events of the library
     */
    var logger: (Any) -> Unit = {}
}