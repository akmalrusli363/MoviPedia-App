package com.tilikki.movipedia.model

private const val PLACEHOLDER_SYMBOL = "[<%>]"
private const val NULL_URL_PATH = "about:blank"

enum class VideoProvider(
    val site: String,
    private val url: String,
    private val thumbnailUrl: String,
) {
    YOUTUBE(
        "YouTube",
        "https://youtu.be/$PLACEHOLDER_SYMBOL",
        "https://i.ytimg.com/vi/$PLACEHOLDER_SYMBOL/mqdefault.jpg"
    ),
    VIMEO(
        "Vimeo",
        "https://vimeo.com/$PLACEHOLDER_SYMBOL",
        "https://vumbnail.com/$PLACEHOLDER_SYMBOL.jpg"
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