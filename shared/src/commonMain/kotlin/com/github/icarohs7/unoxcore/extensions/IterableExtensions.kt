package com.github.icarohs7.unoxcore.extensions

import kotlin.math.roundToInt

/**
 * Split the iterable in half and
 * return the 2 resulting lists
 * wrapped in a [Pair]
 */
fun <T> Iterable<T>.partition(): Pair<List<T>, List<T>> {
    val halfSize = (count().toDouble() / 2).roundToInt()
    val (half1, half2) = Pair(mutableListOf<T>(), mutableListOf<T>())

    forEachIndexed { index, item -> if (index < halfSize) half1 += item else half2 += item }

    return Pair(half1, half2)
}