package com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch

import com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch.data.DataWebComics
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface WebComicsJpaOpenSearchAdapter : ElasticsearchRepository<DataWebComics, String> {
    fun findFirstByOrderByIdDesc(): DataWebComics?
}
