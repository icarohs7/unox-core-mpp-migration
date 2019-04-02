package com.github.icarohs7.unoxcore.utils

import kotlin.test.assertEquals

infix fun Any?.shouldEqual(other: Any?) {
    assertEquals(actual = this, expected = other)
}