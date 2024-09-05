package com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch.mappers

import com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch.data.DataWebComics
import com.yonatankarp.xkcddatahublite.domain.entity.WebComics

object DataWebComicsMapper {
    fun WebComics.toData() =
        DataWebComics(
            id = id,
            year = year,
            month = month,
            day = day,
            title = title,
            safeTitle = safeTitle,
            transcript = transcript,
            alternativeText = alternativeText,
            imageUrl = imageUrl,
            news = news,
            link = link,
        )

    fun DataWebComics.toDomain() =
        WebComics(
            id = id,
            year = year,
            month = month,
            day = day,
            title = title,
            safeTitle = safeTitle,
            transcript = transcript,
            alternativeText = alternativeText,
            imageUrl = imageUrl,
            news = news,
            link = link,
        )
}
