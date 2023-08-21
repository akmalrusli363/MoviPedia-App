package com.tilikki.movipedia.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tilikki.movipedia.model.general.Language

@Entity(tableName = "languages")
data class EntityLanguage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val languageCode: String,
    val name: String,
) {
    fun toDomainLanguage(): Language {
        return Language(languageCode, name)
    }
}