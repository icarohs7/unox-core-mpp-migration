package com.github.icarohs7.unoxcore.extensions

/**
 * Retrieve the value of the object or return the fallback if null,
 * useful when chaining operations
 */
fun <T> T?.valueOr(fallback: T): T = this ?: fallback