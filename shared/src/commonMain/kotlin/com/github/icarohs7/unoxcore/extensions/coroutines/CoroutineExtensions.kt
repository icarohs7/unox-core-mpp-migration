package com.github.icarohs7.unoxcore.extensions.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * Cancel the coroutine scope
 */
@Suppress("NOTHING_TO_INLINE")
inline fun CoroutineScope.cancelCoroutineScope(): Unit = this.cancel()

/**
 * The job used by this [CoroutineScope]
 */
inline val CoroutineScope.job: Job
    get() = coroutineContext[Job] ?: error("This coroutine scope doesn't have a job: $this")

/**
 * Default [CoroutineDispatcher] used in the scope
 */
inline val CoroutineScope.dispatcher: CoroutineDispatcher
    get() = coroutineContext.dispatcher

/**
 * [CoroutineDispatcher] in which the coroutine is being executed
 */
inline val CoroutineContext.dispatcher: CoroutineDispatcher
    get() = this[ContinuationInterceptor] as? CoroutineDispatcher ?: error("Coroutine dispatcher not found")


/**
 * Consume each element sent by the channel, suspending when there's
 * none until the first element comes in
 */
suspend inline fun <T> ReceiveChannel<T>.forEach(fn: (T) -> Unit) {
    for (item in this) fn(item)
}

private typealias Suspend<T> = suspend () -> T

/**
 * Execute both functions in parallel and suspend until
 * both are complete, then returning the result of both
 * wrapped in a [Pair]
 */
suspend fun <A, B> parallelPair(op1: Suspend<A>, op2: suspend () -> B): Pair<A, B> {
    return coroutineScope {
        val a = async { op1() }
        val b = async { op2() }
        Pair(a.await(), b.await())
    }
}

/**
 * Execute the three functions in parallel and suspend until
 * they are complete, then returning the result wrapped in
 * a [Triple]
 */
suspend fun <A, B, C> parallelTriple(op1: Suspend<A>, op2: Suspend<B>, op3: Suspend<C>): Triple<A, B, C> {
    return coroutineScope {
        val a = async { op1() }
        val b = async { op2() }
        val c = async { op3() }
        Triple(a.await(), b.await(), c.await())
    }
}

/**
 * Execute all the given operations in parallel
 * using the coroutine context of the calling
 * coroutine ([kotlin.coroutines.EmptyCoroutineContext])
 */
suspend inline fun parallelRun(vararg operations: suspend () -> Unit) {
    coroutineScope { operations.map { async { it() } } }.awaitAll()
}

/**
 * [Iterable.map] function executing
 * each operation in parallel and then
 * joining the result and returning it
 */
suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> {
    return coroutineScope {
        map { async { f(it) } }.awaitAll()
    }
}

/**
 * [Iterable.filter] function executing
 * each operation in parallel and then
 * joining the resulting elements market
 * to be kept and returning the new filtered
 * collection
 */
suspend fun <A : Any> Iterable<A>.parallelFilter(predicate: suspend (A) -> Boolean): List<A> {
    return coroutineScope {
        parallelMap { if (predicate(it)) it else null }.filterNotNull()
    }
}