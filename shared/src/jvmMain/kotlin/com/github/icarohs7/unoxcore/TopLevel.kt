package com.github.icarohs7.unoxcore

import arrow.core.Try
import arrow.effects.IO
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import kotlinx.coroutines.CoroutineScope

/**
 * Invoke the given function that will cause side effects
 * returning its result wrapped in an [IO] instance
 */
fun <A> sideEffect(f: () -> A): IO<A> {
    return Try { f() }
            .fold({ IO.raiseError<A>(it) }, { IO.just(it) })
            .also(UnoxCore.logger)
}

/**
 * Invoke the given function that will cause side effects
 * on a background coroutine, returning its result wrapped
 * in an [IO] instance
 */
suspend fun <A> sideEffectBg(f: suspend CoroutineScope.() -> A): IO<A> {
    return onBackground {
        Try { f() }
                .fold({ IO.raiseError<A>(it) }, { IO.just(it) })
                .also(UnoxCore.logger)
    }
}