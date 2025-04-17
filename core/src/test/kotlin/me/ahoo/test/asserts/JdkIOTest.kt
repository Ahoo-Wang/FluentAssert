package me.ahoo.test.asserts

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.FileAssert
import org.assertj.core.api.PathAssert
import org.assertj.core.api.UriAssert
import org.assertj.core.api.UrlAssert
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.Path

class JdkIOTest {

    @Test
    fun `given Path when assert then PathAssert`() {
        val value = mockk<Path>()
        assertThat(value.assert()).isInstanceOf(PathAssert::class.java)
    }

    @Test
    fun `given File when assert then FileAssert`() {
        val value = mockk<File>()
        assertThat(value.assert()).isInstanceOf(FileAssert::class.java)
    }

    @Test
    fun `given URL when assert then UrlAssert`() {
        val value = mockk<URL>()
        assertThat(value.assert()).isInstanceOf(UrlAssert::class.java)
    }

    @Test
    fun `given URI when assert then UriAssert`() {
        val value = mockk<URI>()
        assertThat(value.assert()).isInstanceOf(UriAssert::class.java)
    }
}
