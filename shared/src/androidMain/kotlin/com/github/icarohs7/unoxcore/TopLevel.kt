@file:JvmName("TopLevelKtAndroid")

package com.github.icarohs7.unoxcore

import android.os.Handler
import android.os.Looper

/**
 * Execute the block right away if on main thread, or schedule it
 * to be executed on the main thread otherwise
 */
fun mustRunOnMainThread(fn: () -> Unit) {
    val mainLooper = Looper.getMainLooper()
    val isOnMainLooper = (Looper.myLooper() == mainLooper)

    if (isOnMainLooper) fn()
    else Handler(mainLooper).post(fn)
}