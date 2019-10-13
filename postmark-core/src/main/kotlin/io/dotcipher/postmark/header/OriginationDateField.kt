package io.dotcipher.postmark.header

import io.dotcipher.postmark.FoldableString

/**
 * Origination Date Field, as specified as a header within
 * [RFC2822#section-3.6.1](https://tools.ietf.org/html/rfc2822#section-3.6.1).
 */
data class OriginationDateField(override val value: FoldableString) : Header() {

    override val name: String = NAME

    override val type: FieldType = FieldType.ORIGINATION_DATE

    val dateTime by lazy {
        value.parseAsOffsetDateTime()
    }

    companion object {
        private const val NAME = "Date"
    }

}
