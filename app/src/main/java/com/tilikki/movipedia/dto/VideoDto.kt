package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.Video
import com.tilikki.movipedia.model.VideoType
import com.tilikki.movipedia.util.DateParser
import java.text.SimpleDateFormat

data class VideoDto(
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val resolution: Int,
    @SerializedName("type") val type: String,
    @SerializedName("official") val official: Boolean,
    @SerializedName("published_at") val published_at: String,
    @SerializedName("id") val id: String
) {
    fun toDomainVideo(): Video {
        val convertedPublishedDate = DateParser.convertDateFormat(
            inputDate = published_at,
            inputDateFormat = DateParser.ISO_DATE_TIME_INSTANT,
            outputDateStyle = SimpleDateFormat.LONG
        )
        return Video(
            id = id,
            language = language,
            country = country,
            name = name,
            key = key,
            site = site,
            resolution = resolution,
            type = VideoType.getVideoType(type),
            official = official,
            publishedDate = convertedPublishedDate,
        )
    }
}
