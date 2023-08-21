package com.tilikki.movipedia.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tilikki.movipedia.db.entity.EntityCountry
import com.tilikki.movipedia.db.entity.EntityLanguage
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AppConfigDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLanguageList(languages: List<EntityLanguage>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryList(countries: List<EntityCountry>)

    @Query("SELECT * FROM languages")
    fun getAllLanguages(): Observable<List<EntityLanguage>>

    @Query("SELECT * FROM countries")
    fun getAllCountries(): Observable<List<EntityCountry>>

    @Query("SELECT * FROM languages WHERE languageCode = :languageCode")
    fun getLanguageByLanguageCode(languageCode: String): Single<EntityLanguage>

    @Query("SELECT * FROM countries WHERE countryCode = :countryCode")
    fun getCountryByCountryCode(countryCode: String): Single<EntityCountry>

    @Query("DELETE FROM languages")
    fun deleteAllLanguages(): Single<Int>

    @Query("DELETE FROM countries")
    fun deleteAllCountries(): Single<Int>
}