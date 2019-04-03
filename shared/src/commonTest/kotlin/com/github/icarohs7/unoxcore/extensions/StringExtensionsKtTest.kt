package com.github.icarohs7.unoxcore.extensions

import com.github.icarohs7.unoxcore.utils.shouldEqual
import kotlin.test.Test
import kotlin.test.fail

class StringExtensionsKtTest {

    @Test
    fun should_use_a_fallback_string() {
        val s1 = " "
        val f1 = "leeeeeroy jeeeeenkins!"

        (s1 ifBlankOrNull f1) shouldEqual f1
        (s1 ifEmptyOrNull f1) shouldEqual s1

        val s2: String? = null
        val f2 = "test"

        (s2 ifBlankOrNull f2) shouldEqual f2
        (s2 ifEmptyOrNull f2) shouldEqual f2

        val s3 = "hi"
        val f3 = "nope"

        (s3 ifBlankOrNull f3) shouldEqual s3
        (s3 ifEmptyOrNull f3) shouldEqual s3

        val s4 = " "
        val f4 = "leeeeeroy jeeeeenkins!"

        (s4 ifBlankOrNull { f4 }) shouldEqual f4
        (s4 ifEmptyOrNull { f4 }) shouldEqual s4

        val s5: String? = null
        val f5 = "test"

        (s5 ifBlankOrNull { f5 }) shouldEqual f5
        (s5 ifEmptyOrNull { f5 }) shouldEqual f5

        val s6 = "hi"
        val f6 = "nope"

        (s6 ifBlankOrNull { f6 }) shouldEqual s6
        (s6 ifEmptyOrNull { f6 }) shouldEqual s6

        val s7 = " "
        try {
            s7 ifBlankOrNull { throw IllegalStateException() }
            fail("Should've thrown exception IllegalStateException")
        } catch (e: IllegalStateException) {
        }

        val s8 = ""
        try {
            s8 ifEmptyOrNull { throw IllegalArgumentException() }
            fail("Should've throw exception IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
        }

        val s9 = ""
        val f9 = "NANI!?"
        (s9 ifBlankOrNull f9) shouldEqual f9
        (s9 ifEmptyOrNull f9) shouldEqual f9
    }

    @Test
    fun find() {
        val s1 = "hello, wor1.2.3ld".find(Regex("\\d.\\d.\\d"))
        val exp1 = "1.2.3"
        s1 shouldEqual exp1

        val s2 = "omai wa mou shindeiru!".find(Regex("o\\w{2}i"))
        val exp2 = "omai"
        s2 shouldEqual exp2

        val s3 = "15lsdkasdklskdlklsadk32osdaklkdlksdlksld99".find(Regex("\\w{2}\\d+"))
        val exp3 = "dk32"
        s3 shouldEqual exp3

        val s4 = "djaskdjsakdjkmudamudamudamudamudadkjajdsoraoraoraoraoraoraora".find(Regex("NANI!"))
        s4 shouldEqual null
    }

    @Test
    fun should_return_only_matching_part_of_string() {
        val s1 = "uuddlrlrba"
        val r1 = "lrlr"
        val rgx1 = Regex("""lr""")
        s1.onlyMatching(rgx1) shouldEqual r1

        val s2 = "aabbaaabababasfsfuzumymw"
        val r2 = "abbababab"
        val rgx2 = Regex("""ab+""")
        s2.onlyMatching(rgx2) shouldEqual r2

        val s3 = "afffzzabcxyaababxxffzauukkabc"
        val r3 = "fzfz"
        val rgx3 = Regex("""fz""")
        s3.onlyMatching(rgx3) shouldEqual r3
    }

    @Test
    fun should_return_only_the_digits_of_a_string() {
        val s1 = "123ajjkdsjakdj456dkjakjdkjasd789"
        val r1 = "123456789"
        s1.onlyNumbers() shouldEqual r1

        val s2 = "dasdasdas12a21b456c654ajdksdjkajdkas"
        val r2 = "1221456654"
        s2.onlyNumbers() shouldEqual r2
    }

    @Test
    fun should_get_the_digits_in_a_string() {
        val s1 = "123ajjkdsjakdj456dkjakjdkjasd789"
        val r1 = 123456789L
        s1.digits() shouldEqual r1

        val s2 = "dasdasdas12a21b456c654ajdksdjkajdkas"
        val r2 = 1221456654L
        s2.digits() shouldEqual r2

        val s3 = "1dskadkokwo3oskodkaodkao4okoaskdokasodkod99daksokd1532oskdaokd"
        val r3 = 134991532L
        s3.digits() shouldEqual r3
    }
}