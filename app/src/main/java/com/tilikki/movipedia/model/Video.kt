package com.tilikki.movipedia.model

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id") val id: String,
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val resolution: Int,
    @SerializedName("type") val type: VideoType,
    @SerializedName("official") val official: Boolean,
    @SerializedName("published_at") val publishedDate: String,
) {
    fun getVideoUrl(): String {
        return VideoProvider.getVideoProvider(site).getVideoUrl(key)
    }

    fun getThumbnailUrl(): String {
        return VideoProvider.getVideoProvider(site).getVideoThumbnailUrl(key)
    }
}
