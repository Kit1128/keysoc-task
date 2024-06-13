package com.example.keysoccodingtask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.keysoccodingtask.database.AppDatabase
import com.example.keysoccodingtask.retrofit.model.Album
import com.example.keysoccodingtask.retrofit.request.RetrofitInstance
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums
    private val _showingBookmarks = MutableLiveData<Boolean>(false)
    val showingBookmarks: LiveData<Boolean> = _showingBookmarks

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val albumDao = AppDatabase.getInstance(application).getAlbumDao()

    fun getAlbums(searchTerm: String, entity: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAlbums(searchTerm, entity)
                if (response.isSuccessful && response.body() != null) {
                    _albums.postValue(response.body()!!.results)
                    albumDao.deleteAndInsert(response.body()!!.results)
                } else {
                    _status.postValue("Failed to load albums: ${response.message()}")
                }
            } catch (e: Exception) {
                _status.postValue("Failed to load albums: ${e.message}")
            }
        }
    }

    fun setBookmarkStatus(album: Album) {
        viewModelScope.launch {
            try {
                val newStatus = !album.isBookmarked
                albumDao.updateBookmark(album.collectionId, newStatus)
                updateAlbumInLiveData(album, newStatus)
            } catch (e: Exception) {
                _status.postValue("Error updating bookmark: ${e.message}")
            }
        }
    }

    fun toggleBookmarksDisplay() {
        _showingBookmarks.value = !_showingBookmarks.value!!
    }

    val bookmarkedAlbums: LiveData<List<Album>> = albumDao.getBookmarked()

    private fun updateAlbumInLiveData(album: Album, newStatus: Boolean) {
        val updatedList = _albums.value?.map {
            if (it.collectionId == album.collectionId) it.copy(isBookmarked = newStatus) else it
        }
        _albums.postValue(updatedList)
    }
}