package com.yonatankarp.xkcddatahublite.application.ports

import com.yonatankarp.xkcddatahublite.domain.entity.WebComics

interface XkcdClientPort {
    suspend fun getLatestComicId(): Int

    suspend fun getComicsById(id: Int): WebComics
}
