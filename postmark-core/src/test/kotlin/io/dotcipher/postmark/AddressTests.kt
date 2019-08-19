package io.dotcipher.postmark

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isFalse
import strikt.assertions.isNotBlank
import strikt.assertions.isTrue

class AddressTests {

    @ParameterizedTest
    @CsvFileSource(resources = ["/address/valid/email_address.csv"])
    fun testValidEmailAddress(string: String) {
        val email = EmailAddress.of(string)

        expectThat(EmailAddress.isValid(string)).isTrue()
        expectThat(email) {
            get { localName }.isNotBlank()
            get { domain }.isNotBlank()
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/address/invalid/email_address.csv"])
    fun testInvalidEmailAddress(string: String) {
        expectThat(EmailAddress.isValid(string)).isFalse()
        expectThrows<IllegalArgumentException> { EmailAddress.of(string) }
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/address/valid/mailbox_address.csv"])
    fun testValidMailboxAddress(string: String) {
        val mailbox = MailboxAddress.of(string)

        expectThat(MailboxAddress.isValid(string)).isTrue()
        expectThat(mailbox) {
            get { name }.isNotBlank()
            get { localName }.isNotBlank()
            get { domain }.isNotBlank()
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/address/invalid/mailbox_address.csv"])
    fun testInvalidMailboxAddress(string: String) {
        expectThat(MailboxAddress.isValid(string)).isFalse()
        expectThrows<IllegalArgumentException> { MailboxAddress.of(string) }
    }

}
