package com.example.keysoccodingtask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keysoccodingtask.database.dao.AlbumDao
import com.example.keysoccodingtask.retrofit.model.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAlbumDao(): AlbumDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "album_database.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}