package com.tilikki.movipedia.model

private const val PLACEHOLDER_SYMBOL = "[<%>]"
private const val NULL_URL_PATH = "about:blank"

enum class VideoProvider(val site: String, val url: String, val thumbnailUrl: String) {
    YOUTUBE(
        "YouTube",
        "https://youtu.be/$PLACEHOLDER_SYMBOL",
        "https://i.ytimg.com/vi/$PLACEHOLDER_SYMBOL/maxresdefault.jpg"
    ),
    UNKNOWN("unknown", NULL_URL_PATH, NULL_URL_PATH);

    companion object {
        fun getVideoProvider(site: String): VideoProvider {
            for (provider in values()) {
                if (site.equals(provider.site, ignoreCase = true)) {
                    return provider
                }
            }
            return UNKNOWN
        }
    }

    fun getVideoUrl(videoId: String): String {
        return url.replace(PLACEHOLDER_SYMBOL, videoId)
    }

    fun getVideoThumbnailUrl(videoId: String): String {
        return thumbnailUrl.replace(PLACEHOLDER_SYMBOL, videoId)
    }

    override fun toString(): String {
        return site
    }
}