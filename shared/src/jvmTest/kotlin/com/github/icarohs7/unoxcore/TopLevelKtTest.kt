package com.github.icarohs7.unoxcore

import arrow.effects.IO
import kotlinx.coroutines.runBlocking
import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.throws

class TopLevelKtTest {
    @Test
    fun should_invoke_side_effect_functions() {
        var v1 = 0
        val r1 = sideEffect { v1 = 10; v1 }
        v1 shouldEqual 10
        r1 shouldEqual IO.just(10)

        val r2 = sideEffect { throw RuntimeException() }
        ;{ r2.unsafeRunSync() } throws RuntimeException::class
    }

    @Test
    fun should_invoke_side_effect_functions_on_background_coroutine() {
        runBlocking {
            var v1 = 0
            val r1 = sideEffectBg { v1 = 10; v1 }
            v1 shouldEqual 10
            r1 shouldEqual IO.just(10)

            val r2 = sideEffectBg { throw IllegalArgumentException() }
            ;{ r2.unsafeRunSync() } throws IllegalArgumentException::class
        }
    }
}