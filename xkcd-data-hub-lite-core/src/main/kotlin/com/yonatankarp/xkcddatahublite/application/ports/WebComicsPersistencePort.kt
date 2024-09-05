package com.yonatankarp.xkcddatahublite.application.ports

import com.yonatankarp.xkcddatahublite.domain.entity.WebComics

interface WebComicsPersistencePort {
    fun findLastStoredId(): Int

    fun save(comics: WebComics): WebComics
}
