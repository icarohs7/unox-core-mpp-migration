package com.github.icarohs7.unoxcore.extensions

import androidx.appcompat.app.AppCompatActivity
import com.github.icarohs7.unoxcore.TestApplication
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class RxJavaExtensionsKtAndroidTest {

    @Test
    fun should_observe_flowable_using_lifecycle__onNext() {
        var c = 0
        var latest = 0
        testFlowableOnActivity<Int>({ c++;latest = it }, actions = listOf(act { onNext(10) }, act { onNext(20) }))

        c shouldEqual 2
        latest shouldEqual 20
    }

    @Test
    fun should_observe_flowable_using_lifecycle__onNext__onError() {
        var c = 0
        var latest = 0
        var e = 0
        testFlowableOnActivity<Int>({ c++;latest = it }, { e++ }, actions = listOf(
                act { onNext(10) },
                act { onNext(20) },
                act { onError(NumberFormatException()) }
        ))

        c shouldEqual 2
        latest shouldEqual 20
        e shouldEqual 1

        var c2 = 0
        var latest2 = 0
        var e2 = 0
        testFlowableOnActivity<Int>({ c2++;latest2 = it }, { e2 = 1532 }, actions = listOf(
                act { onNext(10) },
                act { onNext(20) },
                act { onNext(30) },
                act { onError(NumberFormatException()) },
                act { onNext(40) }
        ))

        c2 shouldEqual 3
        latest2 shouldEqual 30
        e2 shouldEqual 1532
    }

    @Test
    fun should_observe_flowable_using_lifecycle__onNext__onError__onComplete() {
        var c = 0
        var latest = 0
        var e = 0
        var cp = 0
        testFlowableOnActivity<Int>({ c++;latest = it }, { e++ }, { cp = 1532 }, actions = listOf(
                act { onNext(10) },
                act { onNext(20) },
                act { onComplete() }
        ))

        c shouldEqual 2
        latest shouldEqual 20
        e shouldEqual 0
        cp shouldEqual 1532

        var c2 = 0
        var latest2 = 0
        var e2 = 0
        var cp2 = 0

        testFlowableOnActivity<Int>({ c2++;latest2 = it }, { e2 = 1532 }, { cp2++ },
                                    actions = listOf(
                                            act { onNext(10) },
                                            act { onNext(20) },
                                            act { onNext(30) },
                                            act { onError(NumberFormatException()) },
                                            act { onNext(40) }
                                    )
        )

        c2 shouldEqual 3
        latest2 shouldEqual 30
        e2 shouldEqual 1532
        cp2 shouldEqual 0
    }

    private fun <T> testFlowableOnActivity(
            next: (T) -> Unit,
            error: ((Throwable) -> Unit)? = null,
            complete: (() -> Unit)? = null,
            actions: List<Subject<T>.() -> Unit>
    ) {
        val controller = Robolectric.buildActivity(AppCompatActivity::class.java)
        val activity = controller.create().get()

        runBlocking { delay(400) }

        val rx = BehaviorSubject.create<T>()
        val flowable = rx.toFlowable(BackpressureStrategy.LATEST)

        when {
            complete != null && error != null -> {
                flowable.observe(activity, next, error, complete)
            }

            error != null -> {
                flowable.observe(activity, next, error)
            }

            else -> {
                flowable.observe(activity, next)
            }
        }

        runBlocking { delay(400) }

        actions.forEach { action -> action(rx) }

        runBlocking { delay(400) }

        controller.destroy()

        runBlocking { delay(400) }
    }

    private fun <T> act(fn: Subject<T>.() -> Unit): Subject<T>.() -> Unit {
        return fn
    }
}