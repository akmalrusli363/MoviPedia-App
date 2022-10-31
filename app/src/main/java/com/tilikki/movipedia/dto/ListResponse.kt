package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("results") val result: List<T>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResult: Int,
)
