package com.example.keysoccodingtask.retrofit.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumResult(
    val resultCount: Int,
    val results: List<Album>
): Parcelable
