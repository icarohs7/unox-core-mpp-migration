package com.github.icarohs7.unoxcore.extensions

import arrow.core.Failure
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.Success
import arrow.core.Try
import arrow.core.getOrElse
import arrow.core.some
import arrow.core.success
import arrow.core.toOption
import arrow.effects.IO
import org.junit.Test
import se.lovef.assert.throws
import se.lovef.assert.typeIs
import se.lovef.assert.v1.shouldBeTrue
import se.lovef.assert.v1.shouldEqual

class ArrowKtExtensionsKtTest {

    @Test
    fun convert_nullable_value_to_Try() {
        //Given
        val v1: String? = null
        val v2: String? = "Omai wa mou shindeiru"
        val v3: Int? = null
        val v4 = 1532

        //When
        val t1 = v1.toTry()
        val t2 = v2.toTry()
        val t3 = v3.toTry()
        val t4 = v4.toTry()

        //Then
        t1.isFailure().shouldBeTrue()
        ;{ throw t1.failed().getOrElse { Exception() } } throws KotlinNullPointerException::class
        t2.getOrElse { "" } shouldEqual "Omai wa mou shindeiru"
        t3.isFailure().shouldBeTrue()
        ;{ throw t3.failed().getOrElse { Exception() } } throws KotlinNullPointerException::class
        t4.getOrElse { -42 } shouldEqual 1532
    }

    @Test
    fun should_map_option_removing_treating_null_returns() {
        //Given
        val o1 = Some(1)
        val o2 = Some(2)
        val o3 = Some(3)
        //When
        val r1 = o1.nullMap { it + 10 }.nullMap { it * 10 }
        val a1: Int? = null
        val r2 = o2.nullMap { a1 }.nullMap { it * 10 }
        val r3 = o3.nullMap { it * 2 }.nullMap { it * 5 }
        //Then
        r1 shouldEqual Some(110)
        r2 shouldEqual None
        r3 shouldEqual Some(30)
    }

    @Test
    fun should_map_value_to_option_removing_nullability() {
        //Given
        val v1: Int? = 10
        val v2: Int? = null
        val v3: Int? = 1532
        //When
        val a: Int? = null
        val r1 = v1.optionMap { a }
        val r2 = v2.optionMap { it * 2 }
        val r3 = v3.optionMap { it * 2 }
        //Then
        r1 shouldEqual None
        r2 shouldEqual None
        r3 shouldEqual Some(3064)
    }

    @Test
    fun first_of_wrapped_list() {
        val l1 = listOf(1, 2, 3).success()
        val l2 = listOf(1, 2, 3).some()
        val l3 = emptyList<Int>().success()
        val l4 = emptyList<Int>().some()

        l1.first() shouldEqual Try.just(1)
        l2.first() shouldEqual Option.just(1)
        val fn = fun() { throw l3.first().failed().getOrElse { Exception() } }
        fn throws NoSuchElementException::class
        l4.first() shouldEqual None
    }

    @Test
    fun try_string_or_empty_value() {
        //Given
        val t1 = Try { "Omai wa mou shindeiru!" }
        val t2 = Try { throw Exception("wat") }
        val t3 = Try { "NANI!?" }
        //When
        val r1 = t1.orEmpty()
        val r2 = t2.orEmpty()
        val r3 = t3.orEmpty()
        //Then
        r1 shouldEqual "Omai wa mou shindeiru!"
        r2 shouldEqual ""
        r3 shouldEqual "NANI!?"
    }

    @Test
    fun should_map_cathing_exceptions_thrown() {
        //Given
        val t1 = Try { 1 }
        val t2 = Try { "hi" }
        //When
        val r1 = t1.mapCatching { 15 }
        val r2 = t2.mapCatching { it.toInt() }
        //Then
        r1 shouldEqual Success(15)
        r2 typeIs Failure::class
    }

    @Test
    fun get_option_string_or_empty() {
        //Given
        val o1: String? = "Omai wa mou shindeiru!"
        val o2: String? = null
        val o3: String? = "NANI!?"
        //When
        val r1 = o1.toOption().orEmpty()
        val r2 = o2.toOption().orEmpty()
        val r3 = o3.toOption().orEmpty()
        //Then
        r1 shouldEqual "Omai wa mou shindeiru!"
        r2 shouldEqual ""
        r3 shouldEqual "NANI!?"
    }

    @Test
    fun get_option_list_or_empty() {
        //Given
        val l1 = Some(listOf(1, 2, 3))
        val l2 = Option.empty<List<Int>>()
        val l3 = listOf("Omai wa mou shindeiru!", "NANI!?").toOption()
        //When
        val r1 = l1.orEmpty()
        val r2 = l2.orEmpty()
        val r3 = l3.orEmpty()
        //Then
        r1 shouldEqual listOf(1, 2, 3)
        r2 shouldEqual emptyList()
        r3 shouldEqual listOf("Omai wa mou shindeiru!", "NANI!?")
    }

    @Test
    fun try_or_throw() {
        //Given
        val t1 = Try { 1532 }
        val t2 = Try { "Omai wa mou shindeiru" }
        val t3 = Try { "NANI!?" }
        val t4 = Try { throw Exception() }
        val t5 = Try { throw UnsupportedOperationException("wat") }
        //Then
        t1.orThrow() shouldEqual 1532
        t2.orThrow() shouldEqual "Omai wa mou shindeiru"
        t3.orThrow() shouldEqual "NANI!?"
        { t4.orThrow() } throws Exception::class
        { t5.orThrow() } throws UnsupportedOperationException::class
    }

    @Test
    fun should_remove_nullability_from_option() {
        //Given
        val o1 = Some<Int?>(10)
        val o2 = Some<Int?>(null)
        val o3 = Some<String?>("Hey")
        //When
        val r1 = o1.removeNull()
        val r2 = o2.removeNull()
        val r3 = o3.removeNull()
        //Then
        r1 shouldEqual Some(10)
        r2 shouldEqual None
        r3 shouldEqual Some("Hey")
    }

    @Test
    fun should_map_not_null_values() {
        //Given
        val o1 = Some<Int?>(10)
        val o2 = Some<Int?>(null)
        val o3 = Some<String?>("Hey")
        //When
        val r1 = o1.mapNotNull { it * 10 }
        val r2 = o2.mapNotNull { it + 2 }
        val r3 = o3.mapNotNull { "$it you, out there in the cold" }
        //Then
        r1 shouldEqual Some(100)
        r2 shouldEqual None
        r3 shouldEqual Some("Hey you, out there in the cold")
    }

    @Test
    fun should_return_the_list_inside_a_try_or_empty_list() {
        //Given
        val l1 = Try { listOf(1, 2, 3) }
        val l2 = Try { listOf(4, 5, 6) }
        val l3 = Try { listOf(1, 2, 3 / 0) }

        //When
        val ex1 = l1.orEmpty()
        val ex2 = l2.orEmpty()
        val ex3 = l3.orEmpty()

        //Then
        ex1 shouldEqual listOf(1, 2, 3)
        ex2 shouldEqual listOf(4, 5, 6)
        ex3 shouldEqual emptyList()
    }

    @Test
    fun nullMap_test() {
        //Given
        val o1 = 5.optionMap { 1532 }
        val o2 = (null as? Int?).optionMap { 10 }
        val o3 = 1532.optionMap { null }

        //When
        val e1 = o1.orNull()
        val e2 = o2.orNull()
        val e3 = o3.orNull()

        //Then
        e1 shouldEqual 1532
        e2 shouldEqual null
        e3 shouldEqual null
    }

    @Test
    fun optionMap_test() {
        //Given
        val v1: Int? = 10
        val v2: Int? = null
        val v3: String? = "omai wa mou shindeiru"
        val v4: String? = null

        //When
        val e1 = v1.optionMap { it * 2 }
        val e2 = v2.optionMap { it * 3 }
        val e3 = v3.optionMap { it }
        val e4 = v4.optionMap { "$it will be null" }

        //Then
        e1 shouldEqual Some(20)
        e1 typeIs Option.just(0)::class
        e2 shouldEqual None
        e3 shouldEqual Some("omai wa mou shindeiru")
        e3 typeIs Option.just("")::class
        e4 shouldEqual None
    }

    @Test
    fun unwrap_list_of_typeclasses_containing_only_valid_values() {
        //Given
        val t1: List<Success<Int?>> = listOf(1, 2, 3, null, 5, 6, null, 8, 9).map(::Success)
        val t2: List<Success<String?>> = listOf("a", "b", null, "d", "e").map(::Success)
        val o1: List<Some<Int?>> = listOf(1, 2, 3, null, 5, 6, null, 8, 9).map(::Some)
        val o2: List<Some<String?>> = listOf("a", "b", null, "d", "e").map(::Some)
        val t3: Try<List<Int?>> = listOf(1, 2, 3, null, 5, 6, null, 8, 9).success()
        val t4: Try<List<String?>> = listOf("a", "b", null, "d", "e").success()
        val o3: Option<List<Int?>> = listOf(1, 2, 3, null, 5, 6, null, 8, 9).some()
        val o4: Option<List<String?>> = listOf("a", "b", null, "d", "e").some()
        //Then
        t1.successValues() shouldEqual listOf(1, 2, 3, 5, 6, 8, 9)
        t2.successValues() shouldEqual listOf("a", "b", "d", "e")
        o1.existingValues() shouldEqual listOf(1, 2, 3, 5, 6, 8, 9)
        o2.existingValues() shouldEqual listOf("a", "b", "d", "e")
        t3.successValues() shouldEqual listOf(1, 2, 3, 5, 6, 8, 9)
        t4.successValues() shouldEqual listOf("a", "b", "d", "e")
        o3.existingValues() shouldEqual listOf(1, 2, 3, 5, 6, 8, 9)
        o4.existingValues() shouldEqual listOf("a", "b", "d", "e")
    }

    @Test
    fun should_convert_an_IO_instance_to_Try() {
        val io1 = IO.just(10).tryIO()
        val res1 = Try.just(10)
        io1 shouldEqual res1

        val io2 = IO.invoke { throw Exception() }.tryIO()
        io2 typeIs Failure::class
        ;{ io2.orThrow() } throws Exception::class

        val io3 = IO.invoke { throw RuntimeException() }.tryIO()
        io3 typeIs Failure::class
        ;{ io3.orThrow() } throws RuntimeException::class

        val io4 = IO.invoke { throw IllegalStateException() }.tryIO()
        io4 typeIs Failure::class
        ;{ io4.orThrow() } throws IllegalStateException::class

        var eff1 = 10
        val io5 = IO.invoke { eff1 = 20;1532 }
        eff1 shouldEqual 10
        val res5 = Try { 1532 }
        io5.tryIO() shouldEqual res5
        eff1 shouldEqual 20
    }

    @Test
    fun should_get_an_IO_value_or_a_default() {
        val i1 = IO { throw Exception() }
        val r1 = i1.syncGetOr(10)
        r1 shouldEqual 10

        val i2 = IO { 1532 }
        val r2 = i2.syncGetOr(20)
        r2 shouldEqual 1532

        val i3 = IO { throw Exception() }
        val r3 = i3.syncGetOr { 10 }
        r3 shouldEqual 10

        val i4 = IO { 1532 }
        val r4 = i4.syncGetOr { 20 }
        r4 shouldEqual 1532
    }
}