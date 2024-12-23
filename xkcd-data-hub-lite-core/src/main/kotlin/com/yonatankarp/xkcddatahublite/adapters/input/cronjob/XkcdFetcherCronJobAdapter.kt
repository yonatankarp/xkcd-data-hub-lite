package com.yonatankarp.xkcddatahublite.adapters.input.cronjob

import com.yonatankarp.xkcddatahublite.application.usecases.GetAllXkcdComics
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller

@Controller
class XkcdFetcherCronJobAdapter(
    private val getAllXkcdComics: GetAllXkcdComics,
) {
    @Scheduled(cron = "\${xkcd.cronjob.schedule}")
    suspend fun fetchXkcdComics() {
        logger.info("Starting fetch of new xkcd comics")
        getAllXkcdComics()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(XkcdFetcherCronJobAdapter::class.java)
    }
}
