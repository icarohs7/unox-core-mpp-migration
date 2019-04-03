package com.github.icarohs7.unoxcore.extensions

import arrow.core.Try
import arrow.core.getOrElse
import java.text.NumberFormat

/** Return the number or the result of the block if the first is equal or lesser than 0 */
inline fun Int.ifZeroOrLess(fn: () -> Int): Int {
    return this.takeIf { it > 0 } ?: fn()
}

/** Return the number or the fallback if the is first equal or lesser than 0 */
fun Int.ifZeroOrLess(fallback: Int): Int {
    return this.takeIf { it > 0 } ?: fallback
}

/** Convert the double value to it's currency form using default locale format */
fun Double.asCurrency(): String =
        Try { NumberFormat.getCurrencyInstance().format(this) }
                .getOrElse { this.toString() }

/** @return The receiver or 0 if it's null */
@Suppress("UNCHECKED_CAST")
fun <T : Number> T?.orZero(): T {
    return this ?: 0 as T
}