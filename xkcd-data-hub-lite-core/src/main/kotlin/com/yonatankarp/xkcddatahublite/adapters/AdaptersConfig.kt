package com.yonatankarp.xkcddatahublite.adapters

import com.fasterxml.jackson.databind.SerializationFeature
import com.yonatankarp.xkcddatahublite.application.usecases.GetAllXkcdComics
import com.yonatankarp.xkcddatahublite.application.usecases.StoreXkcdComics
import com.yonatankarp.xkcddatahublite.domain.entity.WebComics
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class AdaptersConfig {
    @Bean
    fun httpClient() =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000 // the entire request
                connectTimeoutMillis = 10_000 // connection to be established
            }
        }

    @Bean
    fun dataChannel(
        @Value("\${xkcd.fetch.max-queue-size}") maxQueueSize: Int,
    ) = Channel<WebComics>(capacity = maxQueueSize)

    @Bean
    fun applicationRunner(
        getAllXkcdComics: GetAllXkcdComics,
        storeXkcdComics: StoreXkcdComics,
        @Value("\${xkcd.fetch.on-startup}") fetchOnStartup: Boolean,
    ) = ApplicationRunner {
        CoroutineScope(Dispatchers.Default).launch {
            when (fetchOnStartup) {
                true -> launch { getAllXkcdComics() }
                false -> logger.info("Skipping fetching on startup")
            }

            storeXkcdComics.registerConsumers()
        }
    }

    companion object {
        private val logger = getLogger(AdaptersConfig::class.java)
    }
}
