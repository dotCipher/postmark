package io.dotcipher.postmark

/**
 * See section **2.2.3 Long Header Fields** in [RFC2822](https://tools.ietf.org/html/rfc2822#section-2.2.3).
 */
data class FoldableString(val content: String,
                          val indentSize: Int = DEFAULT_INDENT_SIZE) {

    val contentFolded by lazy {
        fold(content, SOFT_CHARACTER_LIMIT, indentSize)
    }

    override fun toString(): String = contentFolded

    companion object {
        private const val DEFAULT_INDENT_SIZE = 1
        private const val SOFT_CHARACTER_LIMIT = 78

        private const val FOLD_LINE_BREAK = "\r\n"

        private const val FOLD_SPLIT_CHAR = ' '

        internal fun fold(string: String, charLimit: Int,
                          indentSize: Int = DEFAULT_INDENT_SIZE): String {
            require(string.isNotEmpty() && string.isNotBlank()) {
                "Cannot fold string on empty or blank content"
            }
            require(charLimit > 0) {
                "Cannot fold on negative or zero character limit (must be >= 1)"
            }
            require(indentSize >= 0) {
                "Must fold on split indent size >= 0"
            }
            val folded = StringBuilder()
            var charCount = 0
            val splitString = string.split(FOLD_SPLIT_CHAR)
            splitString.forEachIndexed { index, part ->
                if (index < splitString.size - 1) {
                    when {
                        index == 0 -> {
                            folded.append(part)
                            charCount += part.length
                        }
                        part.length + charCount >= charLimit -> {
                            folded.append(FOLD_LINE_BREAK)
                            repeat(indentSize) {
                                folded.append(FOLD_SPLIT_CHAR)
                            }
                            folded.append(part)
                            // count whitespace indent on charCount reset
                            charCount = part.length + indentSize
                        }
                        else -> {
                            folded.append(FOLD_SPLIT_CHAR)
                                    .append(part)
                            charCount += part.length + 1
                        }
                    }
                } else {
                    folded.append(FOLD_SPLIT_CHAR)
                            .append(part)
                    // Don't need to count chars since it's the last iteration
                }
            }
            return folded.toString()
        }

    }

}
