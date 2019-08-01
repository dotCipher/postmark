package io.dotcipher.postmark

sealed class Address {

    abstract val localName: String

    abstract val domain: String

}

/**
 * Standard implementation of the [RFC822](https://www.w3.org/Protocols/rfc822/#z7)
 * structure.  This data model represents the `address` part (*addr-spec* in RFC822) of an
 * InternetAddress, in the format of:
 *
 * **person@place.com**
 *
 * The parsing algorithm uses [Regex] based on [RFC5322](https://www.ietf.org/rfc/rfc5322.txt),
 * but simplified to adhere to the *addr-spec* syntax.
 */
class EmailAddress(override val localName: String,
                   override val domain: String) : Address() {

    override fun toString(): String = "$localName@$domain"

    companion object {
        private const val LOCAL_NAME_PATTERN = "([\\w!#\$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#\$%&’*+/=?`{|}~^-]+)*)"
        private const val DOMAIN_PATTERN = "((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,64})"
        private const val PATTERN = "$LOCAL_NAME_PATTERN@$DOMAIN_PATTERN"

        fun isValid(emailAddress: String): Boolean {
            val matcher = Regex(PATTERN)

        }

        fun of(emailAddress: String): EmailAddress {

        }

    }

}

/**
 * This data structure implements the **mailbox** part of the [RFC822](https://www.w3.org/Protocols/rfc822/#z58)
 * syntax, which contains both the name
 */
class MailboxAddress(val name: String,
                     override val localName: String,
                     override val domain: String) : Address {

}
