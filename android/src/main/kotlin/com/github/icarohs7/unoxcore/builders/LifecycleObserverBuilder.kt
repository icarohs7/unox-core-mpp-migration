package com.github.icarohs7.unoxcore.builders

import androidx.lifecycle.LifecycleOwner

/**
 * Builder used to easily attach lifecycle observer to
 * lifecycle owners through a DSL
 */
class LifecycleObserverBuilder<T : LifecycleOwner> {
    internal var create: T.() -> Unit = {}
    internal var start: T.() -> Unit = {}
    internal var resume: T.() -> Unit = {}
    internal var pause: T.() -> Unit = {}
    internal var stop: T.() -> Unit = {}
    internal var destroy: T.() -> Unit = {}

    fun onCreate(fn: T.() -> Unit) {
        create = fn
    }

    fun onStart(fn: T.() -> Unit) {
        start = fn
    }

    fun onResume(fn: T.() -> Unit) {
        resume = fn
    }

    fun onPause(fn: T.() -> Unit) {
        pause = fn
    }

    fun onStop(fn: T.() -> Unit) {
        stop = fn
    }

    fun onDestroy(fn: T.() -> Unit) {
        destroy = fn
    }
}