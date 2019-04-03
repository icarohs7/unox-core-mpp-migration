package com.github.icarohs7.unoxcore.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


fun <T> mutableLazy(initializer: () -> T): MutableLazy<T> = MutableLazy(initializer)

/**
 * Implementation of the [Lazy] delegate
 * for mutable properties
 */
class MutableLazy<T>(val init: () -> T) : ReadWriteProperty<Any?, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (val v = value) {
            null -> {
                val initResult = init()
                value = initResult
                initResult
            }
            else -> v
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}