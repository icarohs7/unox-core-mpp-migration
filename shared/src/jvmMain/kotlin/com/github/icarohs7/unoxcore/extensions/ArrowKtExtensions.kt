package com.github.icarohs7.unoxcore.extensions

import arrow.core.Failure
import arrow.core.Option
import arrow.core.Success
import arrow.core.Try
import arrow.core.getOrElse
import arrow.core.orNull
import arrow.core.toOption
import arrow.effects.IO

/** Convert a nullable item to a try of it, or a null pointer failure */
fun <T> T?.toTry(): Try<T> =
        Try { this@toTry!! }

/** @return Typeclass safely mapped to the first element of the list */
fun <T> Try<Iterable<T>>.first(): Try<T> =
        this.mapCatching { it.first() }

/** @return Typeclass safely mapped to the first element of the list */
fun <T> Option<Iterable<T>>.first(): Option<T> =
        this.nullMap { it.firstOrNull() }

/** @return Wrapped string or empty */
fun Try<String>.orEmpty(): String =
        this.getOrElse { "" }

/** @return Wrapped string or empty */
fun Option<String>.orEmpty(): String =
        this.getOrElse { "" }

/** @return Wrapped list or an empty one */
fun <T> Try<List<T>>.orEmpty(): List<T> =
        this.getOrElse { emptyList() }

/** @return Wrapped list or an empty one */
fun <T> Option<List<T>>.orEmpty(): List<T> =
        this.getOrElse { emptyList() }

/** @return The wrapped content or throw the given exception when it's a failure */
fun <T> Try<T>.orThrow(): T {
    return when (this) {
        is Success -> value
        is Failure -> throw exception
    }
}

/** Map the wrapped value inside the Try, catching exceptions thrown by the lambda */
inline fun <A, B> Try<A>.mapCatching(f: (A) -> B): Try<B> {
    return this.flatMap { Try { f(it) } }
}

/** @return The option without the wrapped value's nullability, converting it to None if null */
inline fun <A, B> Option<A?>.mapNotNull(f: (A) -> B): Option<B> {
    return this.removeNull().map(f)
}

/** @return The option without the wrapped value's nullability, converting it to None if null */
fun <T> Option<T?>.removeNull(): Option<T> {
    return this.flatMap { it.toOption() }
}

/** Map the value, removing the nullability of the returning value */
inline fun <A, B> Option<A>.nullMap(f: (A) -> B?): Option<B> =
        flatMap { f(it).toOption() }

/** Wrap the nullable receiver into an [Option] and apply the [nullMap] operation */
inline fun <A, B> A?.optionMap(f: (A) -> B?): Option<B> =
        this.toOption().nullMap(f)

/** List with only the elements that are [Success] */
fun <T : Any> Iterable<Try<T?>>.successValues(): List<T> {
    return this.mapNotNull { it.orNull() }
}

/** List with only the elements that are Some */
fun <T : Any> Iterable<Option<T?>>.existingValues(): List<T> {
    return this.mapNotNull { it.orNull() }
}

/** List with only the elements that are [Success] */
fun <T : Any> Try<Iterable<T?>>.successValues(): List<T> {
    return this.map { it.filterNotNull() }.orEmpty()
}

/** List with only the elements that are Some */
fun <T : Any> Option<Iterable<T?>>.existingValues(): List<T> {
    return this.map { it.filterNotNull() }.orEmpty()
}

/**
 * Run an [IO] synchronously, wrapping
 * the result in an instace of [Try]
 */
fun <T> IO<T>.tryIO(): Try<T> {
    return this.attempt().unsafeRunSync().fold(::Failure, ::Success)
}

/**
 * Synchronously run the IO and return it's
 * result or the default value if it fails
 */
fun <T> IO<T>.syncGetOr(default: T): T {
    return this.tryIO().getOrElse { default }
}

/**
 * Synchronously run the IO and return it's
 * result or the result of the given function
 * if it fails
 */
inline fun <T> IO<T>.syncGetOr(default: () -> T): T {
    return this.tryIO().getOrElse { default() }
}