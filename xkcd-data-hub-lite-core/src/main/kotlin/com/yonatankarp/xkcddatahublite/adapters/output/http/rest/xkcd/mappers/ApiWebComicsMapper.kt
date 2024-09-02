package com.yonatankarp.xkcddatahublite.adapters.output.http.rest.xkcd.mappers

import com.yonatankarp.xkcddatahublite.adapters.output.http.rest.xkcd.data.ApiWebComics
import com.yonatankarp.xkcddatahublite.domain.entity.WebComics

object ApiWebComicsMapper {
    fun ApiWebComics.toDomain() =
        WebComics(
            id = id,
            year = year.toInt(),
            month = month.toInt(),
            day = day.toInt(),
            title = title,
            safeTitle = safeTitle,
            transcript = transcript,
            alternativeText = alternativeText,
            imageUrl = imageUrl,
            news = news?.takeIf { it.isNotBlank() },
            link = link?.takeIf { it.isNotBlank() },
        )
}
