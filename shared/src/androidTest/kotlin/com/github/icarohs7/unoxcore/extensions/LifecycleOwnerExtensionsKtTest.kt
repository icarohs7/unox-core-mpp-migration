package com.github.icarohs7.unoxcore.extensions

import androidx.appcompat.app.AppCompatActivity
import com.github.icarohs7.unoxcore.TestApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class LifecycleOwnerExtensionsKtTest {
    @Test
    fun should_add_observer_to_lifecycle_owner_using_dsl() {
        val controller = Robolectric.buildActivity(AppCompatActivity::class.java)
        val activity = controller.get()
        var v = ""

        activity.addOnCreateObserver { v = "What!?" }
        controller.create()
        v shouldEqual "What!?"

        activity.addOnStartObserver { v = "NANI!?" }
        controller.start()
        v shouldEqual "NANI!?"

        activity.addOnResumeObserver { v = "Omai" }
        controller.resume()
        v shouldEqual "Omai"

        activity.addOnPauseObserver { v = "wa" }
        controller.pause()
        v shouldEqual "wa"

        activity.addOnStopObserver { v = "mou" }
        controller.stop()
        v shouldEqual "mou"

        activity.addOnDestroyObserver { v = "shindeiru!" }
        controller.destroy()
        v shouldEqual "shindeiru!"
    }
}