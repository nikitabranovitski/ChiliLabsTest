package com.branovitski.chililab.model

import com.google.gson.annotations.SerializedName

data class GifResponse(

    @SerializedName("data")
    val data: List<GifResponseEntity> = emptyList(),

    @SerializedName("pagination")
    val pagination: Pagination

)

data class GifResponseEntity(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("images")
    val images: Images,

) {
    val url: String
        get() = images.originalImage.url
}

data class Images(
    @SerializedName("original")
    val originalImage: Image
)

data class Image(
    @SerializedName("url")
    val url: String
)

data class Pagination(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("offset")
    val offset: Int
)
