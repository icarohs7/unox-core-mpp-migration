package com.github.icarohs7.unoxcore.delegates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KProperty

/** Delegate to redirect any get operation of the property to the live data */
operator fun <T> LiveData<T>.getValue(thisRef: Any?, property: KProperty<*>): T? = value

/** Delegate to redirect any set operation of the property to the mutable live data */
operator fun <T> MutableLiveData<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    this.value = value
}

/** Helper for [RedirectToLiveDataDelegate] */
fun <T> LiveData<T>.nonNullDelegate(
        defaultValue: T
): RedirectToLiveDataDelegate<T> =
        RedirectToLiveDataDelegate(this, defaultValue)

/**
 * Delegate class used to redirect get and set operations on a property
 * to a given live data using a fallback value for nullsafety,
 * if the livedata isn't mutable, setValue does nothing
 */
open class RedirectToLiveDataDelegate<T>(
        private val liveData: LiveData<T>,
        private val defaultValue: T
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
            liveData.value ?: defaultValue

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (liveData is MutableLiveData) liveData.value = value
    }
}