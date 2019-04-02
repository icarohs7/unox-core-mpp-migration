package com.github.icarohs7.unoxcore.extensions

import com.github.icarohs7.unoxcore.utils.shouldEqual
import com.github.icarohs7.unoxcore.utils.typeIs
import kotlin.test.Test


class CollectionsExtensionsKtTest {
    @Test
    fun should_map_a_map_to_another_type_of_map() {
        //Given
        val m1 = mapOf("name" to "Icaro", "age" to "21")
        val m2 = mapOf(Pair(10, 20), Pair(30, 40))
        //When
        val r1 = m1.associate { (k, v) -> Pair(k.reversed(), v.reversed()) }
        val r2 = m2.associate { (k, v) -> Pair(k * 2, v * 3) }
        //Then
        r1 typeIs Map::class
        r1 shouldEqual mapOf(Pair("eman", "oracI"), Pair("ega", "12"))
        r2 typeIs Map::class
        r2 shouldEqual mapOf(Pair(20, 60), Pair(60, 120))
    }
}