package com.tilikki.movipedia.model

enum class VideoType(private val typeName: String) {
    UNKNOWN("Unknown"),
    TRAILER("Trailer"),
    TEASER("Teaser"),
    BEHIND_THE_SCENE("Behind the Scenes"),
    BLOOPERS("Bloopers"),
    CLIP("Clip"),
    FEATURETTE("Featurette");

    companion object {
        fun getVideoType(videoType: String): VideoType {
            for (vType in values()) {
                if (videoType.equals(vType.typeName, ignoreCase = true)) {
                    return vType
                }
            }
            return UNKNOWN
        }
    }

    override fun toString(): String {
        return typeName
    }
}