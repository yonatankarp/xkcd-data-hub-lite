package com.yonatankarp.xkcddatahublite.adapters.output.http.rest.xkcd.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiWebComics(
    @JsonProperty("num")
    val id: Int,
    val year: String,
    val month: String,
    val day: String,
    val title: String,
    @JsonProperty("safe_title")
    val safeTitle: String,
    val transcript: String,
    @JsonProperty("alt")
    val alternativeText: String,
    @JsonProperty("img")
    val imageUrl: String,
    val news: String?,
    val link: String?,
)
