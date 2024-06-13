package com.example.keysoccodingtask.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.keysoccodingtask.retrofit.model.Album

@Dao
interface AlbumDao {

    @Query("Select * From albums")
    fun getAllAsync(): LiveData<List<Album>>

    @Query("Select * From albums Where isBookmarked = 1")
    fun getBookmarked(): LiveData<List<Album>>

    @Query("UPDATE albums SET isBookmarked = :isBookmarked WHERE collectionId = :collectionId")
    suspend fun updateBookmark(collectionId: Int, isBookmarked: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("Delete From albums")
    suspend fun deleteAllAsync()


    @Transaction
    suspend fun deleteAndInsert(data: List<Album>) {
        deleteAllAsync()
        insertAll(data)
    }
}