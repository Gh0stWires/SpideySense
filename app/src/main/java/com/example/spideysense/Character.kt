package com.example.spideysense


import com.squareup.moshi.Json

data class Character(
    @Json(name = "attributionHTML")
    val attributionHTML: String,
    @Json(name = "attributionText")
    val attributionText: String,
    @Json(name = "code")
    val code: Int,
    @Json(name = "copyright")
    val copyright: String,
    @Json(name = "data")
    val `data`: Data,
    @Json(name = "etag")
    val etag: String,
    @Json(name = "status")
    val status: String
) {
    data class Data(
        @Json(name = "count")
        val count: Int,
        @Json(name = "limit")
        val limit: Int,
        @Json(name = "offset")
        val offset: Int,
        @Json(name = "results")
        val results: List<Result>,
        @Json(name = "total")
        val total: Int
    ) {
        data class Result(
            @Json(name = "comics")
            val comics: Comics,
            @Json(name = "description")
            val description: String,
            @Json(name = "events")
            val events: Events,
            @Json(name = "id")
            val id: Int,
            @Json(name = "modified")
            val modified: String,
            @Json(name = "name")
            val name: String,
            @Json(name = "resourceURI")
            val resourceURI: String,
            @Json(name = "series")
            val series: Series,
            @Json(name = "stories")
            val stories: Stories,
            @Json(name = "thumbnail")
            val thumbnail: Thumbnail,
            @Json(name = "urls")
            val urls: List<Url>
        ) {
            data class Comics(
                @Json(name = "available")
                val available: Int,
                @Json(name = "collectionURI")
                val collectionURI: String,
                @Json(name = "items")
                val items: List<Item>,
                @Json(name = "returned")
                val returned: Int
            ) {
                data class Item(
                    @Json(name = "name")
                    val name: String,
                    @Json(name = "resourceURI")
                    val resourceURI: String
                )
            }

            data class Events(
                @Json(name = "available")
                val available: Int,
                @Json(name = "collectionURI")
                val collectionURI: String,
                @Json(name = "items")
                val items: List<Item>,
                @Json(name = "returned")
                val returned: Int
            ) {
                data class Item(
                    @Json(name = "name")
                    val name: String,
                    @Json(name = "resourceURI")
                    val resourceURI: String
                )
            }

            data class Series(
                @Json(name = "available")
                val available: Int,
                @Json(name = "collectionURI")
                val collectionURI: String,
                @Json(name = "items")
                val items: List<Item>,
                @Json(name = "returned")
                val returned: Int
            ) {
                data class Item(
                    @Json(name = "name")
                    val name: String,
                    @Json(name = "resourceURI")
                    val resourceURI: String
                )
            }

            data class Stories(
                @Json(name = "available")
                val available: Int,
                @Json(name = "collectionURI")
                val collectionURI: String,
                @Json(name = "items")
                val items: List<Item>,
                @Json(name = "returned")
                val returned: Int
            ) {
                data class Item(
                    @Json(name = "name")
                    val name: String,
                    @Json(name = "resourceURI")
                    val resourceURI: String,
                    @Json(name = "type")
                    val type: String
                )
            }

            data class Thumbnail(
                @Json(name = "extension")
                val extension: String,
                @Json(name = "path")
                val path: String
            )

            data class Url(
                @Json(name = "type")
                val type: String,
                @Json(name = "url")
                val url: String
            )
        }
    }
}