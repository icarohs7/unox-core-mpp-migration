package com.github.icarohs7.unoxcore.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> delegateAccess(
        getter: () -> T,
        vararg setters: (T) -> Unit
): PropertyAccessDelegator<T> = PropertyAccessDelegator(getter, *setters)

fun <T> delegateAccess(
        builder: PropertyAccessDelegator.Builder<T>.() -> Unit
): PropertyAccessDelegator<T> =
        PropertyAccessDelegator.Builder<T>()
                .apply(builder)
                .run { PropertyAccessDelegator(getter, *setters) }

/**
 * Delegate setting the given [getter] as the getter of the
 * property the a list of setters to be called with a given value
 * when this property is set
 */
class PropertyAccessDelegator<T>(
        private val getter: () -> T,
        private vararg val setters: (T) -> Unit
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setters.forEach { setter -> setter(value) }
    }

    class Builder<T> {
        lateinit var getter: () -> T
        lateinit var setters: Array<(T) -> Unit>
    }
}