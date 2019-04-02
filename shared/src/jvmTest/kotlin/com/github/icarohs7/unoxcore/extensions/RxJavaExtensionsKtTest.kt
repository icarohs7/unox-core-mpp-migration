package com.github.icarohs7.unoxcore.extensions

import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import se.lovef.assert.v1.shouldContain

class RxJavaExtensionsKtTest {
    @Test
    fun combine_2_flowables() {
        val f1 = Flowable.just(10)
        val f2 = Flowable.just(20)
        val comb = f1 + f2

        testFlowable(comb, 1, Tuple2(10, 20))

        val f3 = Flowable.just("Omai wa")
        val f4 = Flowable.just("mou shindeiru!")
        val comb2 = f3 + f4

        testFlowable(comb2, 1, Tuple2("Omai wa", "mou shindeiru!"))
    }

    @Test
    fun combine_3_flowables() {
        val f1 = Flowable.just("A")
        val f2 = Flowable.just("B")
        val f3 = Flowable.just("C")
        val comb = f1 + f2 + f3

        testFlowable(comb, 1, Tuple3("A", "B", "C"))
    }

    @Test
    fun combine_4_flowables() {
        val f1 = Flowable.just(10L)
        val f2 = Flowable.just(20L)
        val f3 = Flowable.just(30L)
        val f4 = Flowable.just(40L)
        val comb = f1 + f2 + f3 + f4

        testFlowable(comb, 1, Tuple4(10L, 20L, 30L, 40L))
    }

    @Test
    fun combine_5_flowables() {
        val f1 = Flowable.just(1.60)
        val f2 = Flowable.just(2.70)
        val f3 = Flowable.just(3.80)
        val f4 = Flowable.just(4.90)
        val f5 = Flowable.just(5.00)
        val comb = f1 + f2 + f3 + f4 + f5

        testFlowable(comb, 1, Tuple5(1.60, 2.70, 3.80, 4.90, 5.00))
    }

    @Test
    fun combine_6_flowables() {
        val f1 = Flowable.just(true)
        val f2 = Flowable.just(false)
        val f3 = Flowable.just(10)
        val f4 = Flowable.just(20)
        val f5 = Flowable.just("Omai wa mou shindeiru!")
        val f6 = Flowable.just("NANI!?")
        val comb = f1 + f2 + f3 + f4 + f5 + f6

        testFlowable(comb, 1, Tuple6(true, false, 10, 20, "Omai wa mou shindeiru!", "NANI!?"))
    }

    @Test
    fun should_suspend_map_a_flowable() {
        val f1 = Flowable.just(10).suspendMap { it * 2 }
        testFlowable(f1, valueCount = 1, expectedEmissions = *arrayOf(20))

        val f2 = Flowable.just('A', 'B').suspendMap { it + 1 }
        testFlowable(f2, 2, 'B', 'C')

        val f3 = Flowable.just(true, false, true, false).suspendMap { !it }
        testFlowable(f3, 4, false, true, false, true)
    }

    @Test
    fun should_suspend_filter_a_flowable() {
        val f1 = Flowable.just(1, 2, 3, 4, 5, 6).suspendFilter { it % 2 == 0 }
        testFlowable(f1, valueCount = 3, expectedEmissions = *arrayOf(2, 4, 6))

        val f2 = Flowable.just("Omai", "Wa", "Mou", "Shindeiru", "NANI!?").suspendFilter { it == "NANI!?" }
        testFlowable(f2, 1, "NANI!?")

        val f3 = Flowable.just(false, true, false, true, false, true).suspendFilter { it }
        testFlowable(f3, 3, true, true, true)
    }

    @Test
    fun should_inner_filter_a_flowable() {
        val f1 = Flowable.just(listOf(1, 2, 3, 4, 5, 6))
        val r1 = f1.innerFilter { it % 2 == 0 }
        testFlowable(r1, 1, listOf(2, 4, 6))

        val f2 = Flowable.just(listOf("A", "B", "C", "A"), listOf("B", "C"))
        val r2 = f2.innerFilter { it == "A" }
        testFlowable(r2, 2, listOf("A", "A"), listOf())
    }

    @Test
    fun should_inner_map_a_flowable() {
        val f1 = Flowable.just(listOf(1, 2, 3, 4, 5, 6))
        val r1 = f1.innerMap { it * it }
        testFlowable(r1, 1, listOf(1, 4, 9, 16, 25, 36))

        val f2 = Flowable.just(listOf("A", "B", "C", "A"), listOf("B", "C"))
        val r2 = f2.innerMap { "NANI!?" }
        testFlowable(r2, 2, listOf("NANI!?", "NANI!?", "NANI!?", "NANI!?"), listOf("NANI!?", "NANI!?"))
    }

    private fun <T> testFlowable(flowable: Flowable<T>, valueCount: Int, vararg expectedEmissions: T) {
        val subscriber = getSubscriber<T>()
        flowable.subscribe(subscriber)

        runBlocking { delay(250) }
        subscriber.assertComplete()
        subscriber.assertValueCount(valueCount)

        val emissions = subscriber.events.first()
        expectedEmissions.forEach { emissions shouldContain it }
        subscriber.dispose()
    }

    private fun <T> getSubscriber(): TestSubscriber<T> {
        return TestSubscriber.create<T>()
    }
}