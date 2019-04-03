package com.github.icarohs7.unoxcore.delegates

import com.github.icarohs7.unoxcore.utils.shouldEqual
import kotlin.test.Test

class MutableLazyKtTest {
    var flag1: Int = 0
    var prop1: Int by mutableLazy {
        flag1 = 10
        20
    }

    @Test
    fun should_initialize_mutable_property_lazily() {
        flag1 shouldEqual 0
        prop1 shouldEqual 20
        flag1 shouldEqual 10

        prop1 = 1532
        flag1 shouldEqual 10
        prop1 shouldEqual 1532
    }
}