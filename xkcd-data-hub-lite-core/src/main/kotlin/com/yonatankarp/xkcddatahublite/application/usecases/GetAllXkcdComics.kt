package com.yonatankarp.xkcddatahublite.application.usecases

import com.yonatankarp.xkcddatahublite.application.ports.XkcdClientPort
import com.yonatankarp.xkcddatahublite.domain.entity.WebComics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GetAllXkcdComics(
    private val client: XkcdClientPort,
    private val channel: SendChannel<WebComics>,
    @Value("\${xkcd.fetch.producers}") private val numberOfProducers: Int,
) {
    suspend operator fun invoke() =
        coroutineScope {
            withContext(Dispatchers.IO) {
                val latestComicId = client.getLatestComicId()
                (1..latestComicId).chunked(latestComicId / numberOfProducers)
                    .forEach { chunk ->
                        launch(Dispatchers.IO) {
                            chunk.forEach { id ->
                                runCatching {
                                    logger.info("Fetching comic $id")
                                    val comics = client.getComicsById(id)
                                    channel.send(comics)
                                }.onFailure { logger.error("Failed to fetch comic $it") }
                            }
                        }
                    }
            }
        }

    companion object {
        private val logger =
            LoggerFactory.getLogger(GetAllXkcdComics::class.java)
    }
}
