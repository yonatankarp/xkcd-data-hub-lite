package com.yonatankarp.xkcddatahublite.adapters.output.persistence.opensearch.data

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "webcomics")
data class DataWebComics(
    @Id
    val id: Int,
    val year: Int,
    val month: Int,
    val day: Int,
    val title: String,
    val safeTitle: String,
    val transcript: String,
    val alternativeText: String,
    val imageUrl: String,
    val news: String? = null,
    val link: String? = null,
)
