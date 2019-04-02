package com.github.icarohs7.unoxcore

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Dispatcher executing coroutines on the
 * UI or Main thread
 */
expect var MPPMainDispatcher: CoroutineDispatcher