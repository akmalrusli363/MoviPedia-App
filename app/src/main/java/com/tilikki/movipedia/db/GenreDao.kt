package com.tilikki.movipedia.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tilikki.movipedia.model.Genre
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)

    @Query("SELECT * FROM genres")
    fun getAll(): Observable<List<Genre>>

    @Query("SELECT * FROM genres WHERE id = :genreId")
    fun getGenreById(genreId: Int): Single<Genre>

    @Query("DELETE FROM genres")
    fun deleteAll(): Single<Int>
}