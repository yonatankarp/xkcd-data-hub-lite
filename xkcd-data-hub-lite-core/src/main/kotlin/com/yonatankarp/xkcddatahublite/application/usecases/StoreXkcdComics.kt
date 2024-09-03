package com.yonatankarp.xkcddatahublite.application.usecases

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
    @Value("\${xkcd.fetch.consumers}") private val numberOfConsumers: Int,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) {
    suspend fun registerConsumers() =
        repeat(numberOfConsumers) { index ->
            scope.launch {
                for (data in channel) {
                    logger.info("Coroutine $index received: ${data.id} - ${data.title}")
                }
            }
        }

    companion object {
        private val logger = LoggerFactory.getLogger(StoreXkcdComics::class.java)
    }
}
