package io.dotcipher.postmark

import io.dotcipher.postmark.header.Header

data class EmailMessage(val headers: List<Header>,
                        ) {

}
