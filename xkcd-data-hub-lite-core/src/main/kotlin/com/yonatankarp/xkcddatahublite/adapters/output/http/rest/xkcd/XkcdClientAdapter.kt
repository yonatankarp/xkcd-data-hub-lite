package com.yonatankarp.xkcddatahublite.adapters.output.http.rest.xkcd

import com.yonatankarp.xkcddatahublite.adapters.output.http.rest.xkcd.data.ApiWebComics
import com.yonatankarp.xkcddatahublite.adapters.output.http.rest.xkcd.mappers.ApiWebComicsMapper.toDomain
import com.yonatankarp.xkcddatahublite.application.ports.XkcdClientPort
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class XkcdClientAdapter(
    private val client: HttpClient,
    @Value("\${xkcd.base-url}") private val baseUrl: String,
) : XkcdClientPort {
    override suspend fun getLatestComicId() = client.get("$baseUrl/info.0.json").body<ApiWebComics>().id

    override suspend fun getComicsById(id: Int) =
        client
            .get("$baseUrl/$id/info.0.json")
            .body<ApiWebComics>()
            .toDomain()
}
