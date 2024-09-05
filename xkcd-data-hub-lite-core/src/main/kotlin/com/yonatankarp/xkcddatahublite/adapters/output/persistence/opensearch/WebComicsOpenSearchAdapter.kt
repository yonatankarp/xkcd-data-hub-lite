package com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch

import com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch.mappers.DataWebComicsMapper.toData
import com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch.mappers.DataWebComicsMapper.toDomain
import com.yonatankarp.xkcddatahublite.application.ports.WebComicsPersistencePort
import com.yonatankarp.xkcddatahublite.domain.entity.WebComics
import org.springframework.stereotype.Component

@Component
class WebComicsOpenSearchAdapter(
    private val webComicsJpaOpenSearchAdapter: WebComicsJpaOpenSearchAdapter,
) : WebComicsPersistencePort {
    override fun findLastStoredId() = webComicsJpaOpenSearchAdapter.findFirstByOrderByIdDesc()?.id ?: 0

    override fun save(comics: WebComics): WebComics = webComicsJpaOpenSearchAdapter.save(comics.toData()).toDomain()
}
