package com.yonatankarp.xkcddatahublite.application.usecases

import com.yonatankarp.xkcddatahublite.application.ports.WebComicsPersistencePort
import com.yonatankarp.xkcddatahublite.domain.entity.WebComics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StoreXkcdComics(
    private val channel: ReceiveChannel<WebComics>,
    private val webComicsPersistencePort: WebComicsPersistencePort,
    @Value("\${xkcd.fetch.consumers}") private val numberOfConsumers: Int,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) {
    suspend fun registerConsumers() =
        repeat(numberOfConsumers) {
            scope.launch {
                for (comics in channel) {
                    logger.info("Storing comic ${comics.title}")
                    webComicsPersistencePort.save(comics)
                }
            }
        }

    companion object {
        private val logger = LoggerFactory.getLogger(StoreXkcdComics::class.java)
    }
}
