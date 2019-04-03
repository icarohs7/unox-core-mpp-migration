package com.github.icarohs7.unoxcore.extensions

import arrow.core.Try
import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.throws
import java.util.Locale

class NumberExtensionsKtTest {
    @Test
    fun use_fallback_if_number_is_zero_or_less() {
        val n1 = 1532
        val f1 = 20
        n1.ifZeroOrLess(f1) shouldEqual 1532

        val n2 = 0
        val f2 = 30
        n2.ifZeroOrLess(f2) shouldEqual 30

        val n3 = -24
        val f3 = 42
        n3.ifZeroOrLess(f3) shouldEqual 42

        val n4 = 1532
        val f4 = 20
        n4.ifZeroOrLess { f4 } shouldEqual 1532

        val n5 = 0
        val f5 = 30
        n5.ifZeroOrLess { f5 } shouldEqual 30

        val n6 = -24
        val f6 = 42
        n6.ifZeroOrLess { f6 } shouldEqual 42

        val n7 = 0
        val r7 = Try { n7.ifZeroOrLess { throw IllegalArgumentException() } }
        ;{ r7.orThrow() } throws IllegalArgumentException::class

        val n8 = -24
        val r8 = Try { n8.ifZeroOrLess { throw NegativeArraySizeException() } }
        ;{ r8.orThrow() } throws NegativeArraySizeException::class
    }

    @Test
    fun should_convert_double_number_to_currency_form() {
        Locale.setDefault(Locale.US)
        val n1 = 15.32
        val c1 = "$15.32"
        n1.asCurrency() shouldEqual c1

        Locale.setDefault(Locale("pt", "BR"))
        val n2 = 15.32
        val c2 = "R$ 15,32"
        n2.asCurrency() shouldEqual c2

        Locale.setDefault(Locale.UK)
        val n3 = 15.32
        val c3 = "£15.32"
        n3.asCurrency() shouldEqual c3

        Locale.setDefault(Locale.ITALY)
        val n4 = 15.32
        val c4 = "€ 15,32"
        n4.asCurrency() shouldEqual c4

        Locale.setDefault(Locale.JAPAN)
        val n5 = 15.32
        val c5 = "￥15"
        n5.asCurrency() shouldEqual c5
    }

    @Test
    fun return_number_value_or_zero_if_null() {
        val n1: Int? = 15
        val r1: Int = n1.orZero()
        r1 shouldEqual 15

        val n2: Int? = null
        val r2: Int = n2.orZero()
        r2 shouldEqual 0

        val n3: Float? = 15f
        val r3: Float = n3.orZero()
        r3 shouldEqual 15f

        val n4: Float? = null
        val r4: Float = n4.orZero()
        r4 shouldEqual 0f

        val n5: Double? = 15.0
        val r5: Double = n5.orZero()
        r5 shouldEqual 15.0

        val n6: Double? = null
        val r6: Double = n6.orZero()
        r6 shouldEqual 0.0

        val n7: Byte? = 15
        val r7: Byte = n7.orZero()
        r7 shouldEqual 15

        val n8: Byte? = null
        val r8: Byte = n8.orZero()
        r8 shouldEqual 0

        val n9: Short? = 15
        val r9: Short = n9.orZero()
        r9 shouldEqual 15

        val n10: Short? = null
        val r10: Short = n10.orZero()
        r10 shouldEqual 0

        val n11: Long? = 15L
        val r11: Long = n11.orZero()
        r11 shouldEqual 15L

        val n12: Long? = null
        val r12: Long = n12.orZero()
        r12 shouldEqual 0L
    }
}