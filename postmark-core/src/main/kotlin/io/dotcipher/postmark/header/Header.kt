package io.dotcipher.postmark.header

import io.dotcipher.postmark.FoldableString

/**
 * See [RFC2822#section-3.6](https://tools.ietf.org/html/rfc2822#section-3.6)
 * for more information.
 */
abstract class Header {

    abstract val name: String

    abstract val type: FieldType

    abstract val value: FoldableString

    override fun toString(): String = "$name$DELIMITER$value"

    companion object {
        private const val DELIMITER = ':'
    }

}
//enum class HeaderField {
//    RECEIVED,
//    MESSAGE_ID,
//    SUBJECT,
//    SENDER,
//    FROM,
//    TO,
//    CC,
//    BCC,
//    DATE,
//    CONTENT_TYPE,
//    CONTENT_DISPOSITION,
//    CONTENT_ID;
//
//
//}
