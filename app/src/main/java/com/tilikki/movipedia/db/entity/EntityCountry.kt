package com.tilikki.movipedia.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tilikki.movipedia.model.general.Country

@Entity(tableName = "countries")
data class EntityCountry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val countryCode: String
) {
    fun toDomainCountry(): Country {
        return Country(name, countryCode)
    }
}