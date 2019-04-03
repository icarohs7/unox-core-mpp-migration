package com.github.icarohs7.unoxcore.delegates

import com.github.icarohs7.unoxcore.utils.shouldEqual
import kotlin.test.Test

class PropertyAccessDelegatorKtTest {
    private var target1 = "hello"
    private var mirror1 by delegateAccess(::target1, { target1 = it })
    private var target2 = "omai"
    private var target3 = "wa"
    private var target4 = "mou"
    private var target5 = "shindeiru"
    private var mirror2: String by delegateAccess {
        getter = ::target2
        setters = arrayOf<(String) -> Unit>({ target2 = it }, { target3 = it }, { target4 = it }, { target5 = it })
    }

    @Test
    fun getValue() {
        mirror1 shouldEqual "hello"
        mirror2 shouldEqual "omai"
    }

    @Test
    fun setValue() {
        mirror1 = "WTF!"
        mirror1 shouldEqual "WTF!"
        target1 shouldEqual "WTF!"

        mirror2 = "omai wa mou shindeiru"
        target2 shouldEqual "omai wa mou shindeiru"
        target3 shouldEqual "omai wa mou shindeiru"
        target4 shouldEqual "omai wa mou shindeiru"
        target5 shouldEqual "omai wa mou shindeiru"
    }
}