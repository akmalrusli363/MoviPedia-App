package com.tilikki.movipedia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tilikki.movipedia.db.entity.EntityCountry
import com.tilikki.movipedia.db.entity.EntityLanguage
import com.tilikki.movipedia.model.Genre

@Database(
    entities = [Genre::class, EntityCountry::class, EntityLanguage::class],
    version = 2,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun appConfigDao(): AppConfigDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, "tmdb_movies"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}