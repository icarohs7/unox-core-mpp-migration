package com.github.icarohs7.unoxcore.utils

import kotlin.reflect.KClass
import kotlin.test.assertEquals

infix fun Any?.shouldEqual(other: Any?) {
    assertEquals(actual = this, expected = other)
}

@Suppress("UNUSED_PARAMETER")
inline infix fun <reified T : Any, reified R : Any> T?.typeIs(type: KClass<R>) {
    if (this !is R)
        throw AssertionError("${T::class} is not ${R::class}")
}