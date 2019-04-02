package com.github.icarohs7.unoxcore.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.github.icarohs7.unoxcore.builders.LifecycleObserverBuilder

/**
 * Attach an observable to the lifecycle of a given lifecycle owner using a builder DSL
 */
@Suppress("unused")
fun <T : LifecycleOwner> T.addLifecycleObserver(observer: LifecycleObserverBuilder<T>.() -> Unit) {
    val builder = LifecycleObserverBuilder<T>()
    builder.observer()
    this.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate(): Unit = builder.create(this@addLifecycleObserver)

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart(): Unit = builder.start(this@addLifecycleObserver)

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume(): Unit = builder.resume(this@addLifecycleObserver)

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause(): Unit = builder.pause(this@addLifecycleObserver)

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop(): Unit = builder.stop(this@addLifecycleObserver)

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(): Unit = builder.destroy(this@addLifecycleObserver)
    })
}

/**
 * Attach an observable to the onCreate lifecycle event of the given [LifecycleOwner]
 */
fun <T : LifecycleOwner> T.addOnCreateObserver(observer: T.() -> Unit) {
    addLifecycleObserver { onCreate(observer) }
}

/**
 * Attach an observable to the onStart lifecycle event of the given [LifecycleOwner]
 */
fun <T : LifecycleOwner> T.addOnStartObserver(observer: T.() -> Unit) {
    addLifecycleObserver { onStart(observer) }
}

/**
 * Attach an observable to the onResume lifecycle event of the given [LifecycleOwner]
 */
fun <T : LifecycleOwner> T.addOnResumeObserver(observer: T.() -> Unit) {
    addLifecycleObserver { onResume(observer) }
}

/**
 * Attach an observable to the onPause lifecycle event of the given [LifecycleOwner]
 */
fun <T : LifecycleOwner> T.addOnPauseObserver(observer: T.() -> Unit) {
    addLifecycleObserver { onPause(observer) }
}

/**
 * Attach an observable to the onStop lifecycle event of the given [LifecycleOwner]
 */
fun <T : LifecycleOwner> T.addOnStopObserver(observer: T.() -> Unit) {
    addLifecycleObserver { onStop(observer) }
}

/**
 * Attach an observable to the onDestroy lifecycle event of the given [LifecycleOwner]
 */
fun <T : LifecycleOwner> T.addOnDestroyObserver(observer: T.() -> Unit) {
    addLifecycleObserver { onDestroy(observer) }
}