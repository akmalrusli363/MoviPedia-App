package com.tilikki.movipedia.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EntityLanguage(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val languageCode: String,
    val name: String,
) {
}