@file:JvmName("RxJavaExtensionsKtAndroid")

package com.github.icarohs7.unoxcore.extensions

import androidx.lifecycle.LifecycleOwner
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy

/**
 * Helper used to setup the subscription and
 * observation threads
 */
private fun <T> Flowable<T>.setupThreads(): Flowable<T> {
    return this
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
}

/**
 * Standard process of subscribing to a flowable
 * subscribing on the computation scheduler,
 * observing on the main thread scheduler and
 * auto disposing the subscription when the
 * given lifecycle reaches the destroyed state
 */
fun <T> Flowable<T>.observe(lifecycle: LifecycleOwner, onNext: (T) -> Unit) {
    this
            .setupThreads()
            .subscribe(onNext)
            .disposeBy(lifecycle.onDestroy)
}

/**
 * Standard process of subscribing to a flowable
 * subscribing on the computation scheduler,
 * observing on the main thread scheduler and
 * auto disposing the subscription when the
 * given lifecycle reaches the destroyed state
 */
fun <T> Flowable<T>.observe(lifecycle: LifecycleOwner, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
    this
            .setupThreads()
            .subscribe(onNext, onError)
            .disposeBy(lifecycle.onDestroy)
}

/**
 * Standard process of subscribing to a flowable
 * subscribing on the computation scheduler,
 * observing on the main thread scheduler and
 * auto disposing the subscription when the
 * given lifecycle reaches the destroyed state
 */
fun <T> Flowable<T>.observe(
        lifecycle: LifecycleOwner,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit
) {
    this
            .setupThreads()
            .subscribe(onNext, onError, onComplete)
            .disposeBy(lifecycle.onDestroy)
}