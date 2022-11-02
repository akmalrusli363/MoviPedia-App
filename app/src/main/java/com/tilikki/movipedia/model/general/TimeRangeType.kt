package com.tilikki.movipedia.model.general

enum class TimeRangeType(private val timeRange: String, private val category: String) {
    DAY(timeRange = "day", category = "Daily"),
    WEEK(timeRange = "week", category = "Weekly");

    override fun toString(): String {
        return timeRange
    }
}