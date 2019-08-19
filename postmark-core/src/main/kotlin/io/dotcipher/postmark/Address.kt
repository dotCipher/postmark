package io.dotcipher.postmark


private const val NAME_PATTERN = "\"?([\\w!\"#\\$%&'*+-/=? ]*)\"?"

private const val LOCAL_NAME_PATTERN = "([\\w!#\$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#\$%&’*+/=?`{|}~^-]+)*)"
private const val DOMAIN_PATTERN = "((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,64})"

internal const val LOCAL_NAME_AT_DOMAIN_PATTERN = "$LOCAL_NAME_PATTERN@$DOMAIN_PATTERN"
internal val LOCAL_NAME_AT_DOMAIN_REGEX = Regex(LOCAL_NAME_AT_DOMAIN_PATTERN)


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
data class EmailAddress(override val localName: String,
                        override val domain: String) : Address() {

    override fun toString(): String = "$localName@$domain"

    companion object {

        fun isValid(emailAddress: String): Boolean = LOCAL_NAME_AT_DOMAIN_REGEX.matches(emailAddress)

        fun of(emailAddress: String): EmailAddress = LOCAL_NAME_AT_DOMAIN_REGEX.matchEntire(emailAddress)
                ?.destructured
                ?.let { (localName, domain) -> EmailAddress(localName, domain) }
                ?: throw IllegalArgumentException("Invalid email address '$emailAddress' must match pattern: $LOCAL_NAME_AT_DOMAIN_PATTERN")

    }

}

/**
 * This data structure implements the **mailbox** part of the [RFC822](https://www.w3.org/Protocols/rfc822/#z58)
 * syntax, which contains both the name
 */
data class MailboxAddress(val name: String,
                          override val localName: String,
                          override val domain: String) : Address() {


    companion object {

        private const val PATTERN = "$NAME_PATTERN(?: )<$LOCAL_NAME_AT_DOMAIN_PATTERN>"
        private val REGEX = Regex(PATTERN)

        fun isValid(mailboxAddress: String): Boolean = REGEX.matches(mailboxAddress)

        fun of(mailboxAddress: String): MailboxAddress = REGEX.matchEntire(mailboxAddress)
                ?.destructured
                ?.let { (name, localName, domain) -> MailboxAddress(name, localName, domain) }
                ?: throw IllegalArgumentException("Invalid mailbox address '$mailboxAddress' must match pattern: $PATTERN")

    }

}
