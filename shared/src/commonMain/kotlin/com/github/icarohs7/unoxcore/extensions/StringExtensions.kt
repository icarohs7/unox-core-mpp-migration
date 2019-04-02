package com.github.icarohs7.unoxcore.extensions

/**
 * Returns the parameterized string if the first is null
 * or blank
 */
infix fun String?.ifBlankOrNull(fallback: String): String {
    return if (this.isNullOrBlank()) fallback else "$this"
}

/**
 * Return the result of the invocation of the given
 * function if the receiver is blank or null
 */
inline infix fun String?.ifBlankOrNull(fallback: () -> String): String {
    return if (this.isNullOrBlank()) fallback() else "$this"
}


/**
 * Returns the parameterized string if the first is null
 * or empty
 */
infix fun String?.ifEmptyOrNull(fallback: String): String {
    return if (this.isNullOrEmpty()) fallback else "$this"
}

/**
 * Return the result of the invocation of the given
 * function if the receiver is empty or null
 */
inline infix fun String?.ifEmptyOrNull(fallback: () -> String): String {
    return if (this.isNullOrEmpty()) fallback() else "$this"
}

/**
 * Return the first ocurrence of the substring
 * matching the Regex
 */
fun String.find(regex: Regex): String? =
        regex.find(this)?.value

/**
 * Return only the parts of the
 * string matching the given regex
 */
fun String.onlyMatching(regex: Regex): String {
    return regex.findAll(this).joinToString(separator = "") { it.value }
}

/**
 * Return a string with only the
 * digits contained in the original
 * string
 */
fun String.onlyNumbers(): String {
    val regex = Regex("""\d""")
    return this.onlyMatching(regex)
}

/**
 * Return all digits contained in
 * string, with the order retained
 */
fun String.digits(): Long? {
    return onlyNumbers().toLongOrNull()
}