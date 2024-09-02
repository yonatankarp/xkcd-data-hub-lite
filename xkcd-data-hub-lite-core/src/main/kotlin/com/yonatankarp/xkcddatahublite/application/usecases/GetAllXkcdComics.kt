package com.yonatankarp.xkcddatahublite.application.usecases

import com.yonatankarp.xkcddatahublite.application.ports.XkcdClientPort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetAllXkcdComics(private val client: XkcdClientPort) {
    suspend operator fun invoke() =
        coroutineScope {
            val latestComicId = client.getLatestComicId()
            for (id in 1..latestComicId) {
                withContext(Dispatchers.IO) {
                    runCatching {
                        logger.info("Fetching comic $id")
                        val comics = client.getComicsById(id)
                        println(comics) // TODO: send over channel
                    }.onFailure { logger.error("Failed to fetch comic $it") }
                }
            }
        }

    companion object {
        private val logger =
            LoggerFactory.getLogger(GetAllXkcdComics::class.java)
    }
}
