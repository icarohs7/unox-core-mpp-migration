package com.github.icarohs7.unoxcore.extensions

import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking

/**
 * Combine 2 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
operator fun <A, B> Flowable<A>.plus(
        b: Flowable<B>
): Flowable<Tuple2<A, B>> {
    return Flowable.combineLatest(this, b, BiFunction { a, b ->
        Tuple2(a, b)
    })
}

/**
 * Combine 3 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus2")
operator fun <A, B, C> Flowable<Tuple2<A, B>>.plus(
        c: Flowable<C>
): Flowable<Tuple3<A, B, C>> {
    return Flowable.combineLatest(this, c, BiFunction { t1, t2 ->
        Tuple3(t1.a, t1.b, t2)
    })
}

/**
 * Combine 4 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus3")
operator fun <A, B, C, D> Flowable<Tuple3<A, B, C>>.plus(
        d: Flowable<D>
): Flowable<Tuple4<A, B, C, D>> {
    return Flowable.combineLatest(this, d, BiFunction { t1, t2 ->
        Tuple4(t1.a, t1.b, t1.c, t2)
    })
}

/**
 * Combine 5 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus4")
operator fun <A, B, C, D, E> Flowable<Tuple4<A, B, C, D>>.plus(
        e: Flowable<E>
): Flowable<Tuple5<A, B, C, D, E>> {
    return Flowable.combineLatest(this, e, BiFunction { t1, t2 ->
        Tuple5(t1.a, t1.b, t1.c, t1.d, t2)
    })
}

/**
 * Combine 6 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus5")
operator fun <A, B, C, D, E, F> Flowable<Tuple5<A, B, C, D, E>>.plus(
        f: Flowable<F>
): Flowable<Tuple6<A, B, C, D, E, F>> {
    return Flowable.combineLatest(this, f, BiFunction { t1, t2 ->
        Tuple6(t1.a, t1.b, t1.c, t1.d, t1.e, t2)
    })
}

/**
 * Execute the given suspend map operation and the
 * upstream on the computation scheduler
 */
fun <T : Any, R : Any> Flowable<T>.suspendMap(transform: suspend (T) -> R): Flowable<R> {
    return this
            .map { item -> runBlocking { transform(item) } }
            .subscribeOn(Schedulers.computation())
}

/**
 * Execute the given suspend map operation and the
 * upstream on the computation scheduler
 */
fun <T : Any> Flowable<T>.suspendFilter(predicate: suspend (T) -> Boolean): Flowable<T> {
    return this
            .filter { item -> runBlocking { predicate(item) } }
            .subscribeOn(Schedulers.computation())
}

/**
 * Map the flowable to the filteing of
 * the emitted list using the given
 * predicate
 */
fun <T> Flowable<List<T>>.innerFilter(predicate: (T) -> Boolean): Flowable<List<T>> {
    return this.map { it.filter(predicate) }
}

/**
 * Map the flowable to the mapping of
 * the emitted list using the given
 * transformer
 */
fun <T, R> Flowable<List<T>>.innerMap(transform: (T) -> R): Flowable<List<R>> {
    return this.map { it.map(transform) }
}