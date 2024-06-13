package com.example.keysoccodingtask.retrofit.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "albums")
@Parcelize
data class Album(
    val wrapperType: String,
    val collectionType: String,
    val artistId: Int,
    @PrimaryKey
    val collectionId: Int,
    val amgArtistId: Int?,
    val artistName: String,
    val collectionName: String,
    val collectionCensoredName: String,
    val artistViewUrl: String,
    val collectionViewUrl: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val collectionPrice: Double,
    val collectionExplicitness: String,
    val trackCount: Int,
    val copyright: String,
    val country: String,
    val currency: String,
    val releaseDate: String,
    val primaryGenreName: String,

    var isBookmarked: Boolean = false
) : Parcelable
