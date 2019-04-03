package com.github.icarohs7.unoxcore.extensions.coroutines

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.os.bundleOf
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import se.lovef.assert.v1.shouldBeTrue
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
class CoroutineIntegrationsKtTest {
    @Test
    fun should_receive_broadcasts_using_receive_channel() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        var expected = "0"
        runBlocking {
            val c = context.broadcastChannel(IntentFilter("TEST"))
            val job = async {
                c.forEach { result ->
                    val intent = result.a
                    val name = intent.extras?.get("name")
                    val age = intent.extras?.get("age")
                    expected = "$name of age $age"
                    c.cancel()
                }
            }
            val i = Intent("TEST").apply { putExtras(bundleOf("name" to "Icaro", "age" to 21)) }
            context.sendBroadcast(i)
            job.await()

            c.isClosedForReceive.shouldBeTrue()
            expected shouldEqual "Icaro of age 21"
        }
    }

    @Test
    fun should_await_broadcast() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        runBlocking {
            val job = async {
                context.awaitBroadcast(IntentFilter("TEST")) { (intent, _) ->
                    val name = intent.extras?.get("name")
                    val age = intent.extras?.get("age")
                    "$name of age $age"
                }
            }
            val i = Intent("TEST").apply { putExtras(bundleOf("name" to "Icaro", "age" to 21)) }
            delay(500)
            context.sendBroadcast(i)

            job.await() shouldEqual "Icaro of age 21"
        }
    }
}