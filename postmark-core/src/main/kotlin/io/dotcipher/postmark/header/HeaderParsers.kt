package io.dotcipher.postmark.header

import io.dotcipher.postmark.FoldableString
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private const val DATE_PATTERN = "[A-Z][a-z]{2}, [0-9]{1,2} [A-Z][a-z]{2} [0-9]{4}"
private const val TIME_PATTERN = "[0-9]{2}:[0-9]{2}:[0-9]{2}"
private const val OFFSET_PATTERN = "[+|-][0-9]{4}"
private val REGEX = Regex("(%s %s %s)".format(DATE_PATTERN, TIME_PATTERN, OFFSET_PATTERN))

private val FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME

// Forward
internal fun FoldableString.parseAsOffsetDateTime(): OffsetDateTime =
        content.parseAsOffsetDateTime()

internal fun String.parseAsOffsetDateTime(): OffsetDateTime {
    val match = REGEX.matchEntire(this)
            ?: throw IllegalArgumentException("Unable to parse OffsetDateTime from: $this")
    return match.destructured
            .let { (dateTimeString) -> OffsetDateTime.parse(dateTimeString, FORMATTER) }
}
