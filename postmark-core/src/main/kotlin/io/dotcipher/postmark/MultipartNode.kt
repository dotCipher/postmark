package io.dotcipher.postmark

data class MultipartNode(val contentId: String?,
                         val content: ByteArray,
                         val children: List<MultipartNode>,
                         val mimeType: ) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultipartNode

        if (contentId != other.contentId) return false
        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = contentId?.hashCode() ?: 0
        result = 31 * result + content.contentHashCode()
        return result
    }
}