package com.github.icarohs7.unoxcore.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/** Retrieve the value of the livedata or return the fallback if null */
fun <T> LiveData<T>.valueOr(fallback: T): T =
        value ?: fallback

/**
 * Kotlin wrapper to simplify the
 * observation of a [LiveData]
 */
fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer<T?> { it?.let(observer) })
}