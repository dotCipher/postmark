package io.dotcipher.postmark

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.stream.Stream

class FoldableStringTests {

    @ParameterizedTest
    @ArgumentsSource(FoldTestCasesProvider::class)
    fun testFold(testCase: Triple<String, Int, String>) {
        val folded = FoldableString.fold(testCase.first, testCase.second)
        expectThat(folded).isEqualTo(testCase.third)
    }

    class FoldTestCasesProvider: ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
                FOLD_CASES.map { Arguments.of(it) }.stream()

    }

    companion object {
        private val FOLD_CASES = listOf(
                Triple(
                        "This is a long string with a bunch of content. Blah Blah Blah",
                        15,
                        "This is a long\r\n string with a\r\n bunch of\r\n content. Blah\r\n Blah Blah"
                )
        )
    }

}
