package com.example.keysoccodingtask.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.keysoccodingtask.R
import com.example.keysoccodingtask.adapter.AlbumListAdapter
import com.example.keysoccodingtask.databinding.ActivityMainBinding
import com.example.keysoccodingtask.retrofit.model.Album
import com.example.keysoccodingtask.viewmodel.AlbumViewModel
import com.example.keysoccodingtask.viewmodel.AlbumViewModelFactory

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewModel: AlbumViewModel
    private lateinit var adapter: AlbumListAdapter
    private var currentAlbumsLiveData: LiveData<List<Album>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, AlbumViewModelFactory(application)).get(AlbumViewModel::class.java)

        adapter = AlbumListAdapter(this, object: AlbumListAdapter.BookMarkOnclickListener{
            override fun onBookmarkClicked(album: Album) {
                viewModel.setBookmarkStatus(album)
            }
        })

        binding.rvAlbum.adapter = adapter

        viewModel.showingBookmarks.observe(this, Observer { showingBookmarks ->
            updateIconAndList(showingBookmarks)
        })

        binding.icnBookMark.setOnClickListener {
            viewModel.toggleBookmarksDisplay()
        }

        viewModel.getAlbums("jack+johnson", "album")
    }

    private fun updateIconAndList(showingBookmarks: Boolean) {
        currentAlbumsLiveData?.removeObservers(this)
        val liveData = if (showingBookmarks) viewModel.bookmarkedAlbums else viewModel.albums
        currentAlbumsLiveData = liveData
        liveData.observe(this, Observer { albums ->
            adapter.updateAlbumList(albums)
        })

        val iconRes = if (showingBookmarks) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_border
        binding.icnBookMark.setImageResource(iconRes)
    }

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}