package com.github.icarohs7.unoxcore.extensions

/** Map a given map to another type of map */
inline fun <K, V, K2, V2> Map<K, V>.associate(transformation: (Map.Entry<K, V>) -> Pair<K2, V2>): Map<K2, V2> {
    return this.map(transformation).toMap()
}