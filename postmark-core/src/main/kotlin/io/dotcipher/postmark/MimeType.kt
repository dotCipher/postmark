package io.dotcipher.postmark

import java.nio.charset.Charset

data class MimeType(val type: String,
                    val subtype: String,
                    val parameters: Map<String, Set<String>>) {

    val parsedCharset: Charset? by lazy { charsetOf(this) }

    fun hasWildcard(): Boolean = WILDCARD == type || WILDCARD == subtype

    companion object {

        private const val WILDCARD = "*"

        private const val CHARSET_ATTRIBUTE = "charset"


        fun charsetOf(mimeType: MimeType): Charset? {
            val charsetValues = mimeType.parameters[CHARSET_ATTRIBUTE]
            return when {
                charsetValues == null || charsetValues.isEmpty() -> return null
                charsetValues.size > 1 -> throw IllegalStateException("Multiple charset values defined: " +
                        "$charsetValues")
                else -> Charset.forName(charsetValues.single())
            }
        }

    }

}
