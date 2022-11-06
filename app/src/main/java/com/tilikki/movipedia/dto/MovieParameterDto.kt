package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName

data class MovieParameterDto<T>(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<T>,
)