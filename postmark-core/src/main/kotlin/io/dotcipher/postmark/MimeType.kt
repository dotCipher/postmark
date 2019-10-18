package io.dotcipher.postmark

data class MimeType(val type: String,
                    val subtype: String,
                    val parameters: Map<String, String>) {

}
