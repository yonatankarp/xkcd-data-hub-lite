package com.yonatankarp.xkcddatahublite

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL

@SpringBootTest
@TestConstructor(autowireMode = ALL)
class ApplicationTest {
    @Test
    fun `context loads`() {
    }
}
