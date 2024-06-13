package com.example.keysoccodingtask.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.keysoccodingtask.R
import com.example.keysoccodingtask.databinding.ItemAlbumBinding
import com.example.keysoccodingtask.retrofit.model.Album
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumListAdapter(
    private val context: Context,
    private val listener: BookMarkOnclickListener
) :
    RecyclerView.Adapter<AlbumListAdapter.ViewHolder>() {

    private var albumList: List<Album> = listOf()
    private val dateParse = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun updateAlbumList(newAlbumList: List<Album>) {
        albumList = newAlbumList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val itemAlbumBinding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(itemAlbumBinding.root) {

        init {
            itemAlbumBinding.btnBookMark.setOnClickListener {
                val album = albumList[bindingAdapterPosition]
                listener.onBookmarkClicked(album)
            }
        }

        fun bind(album: Album) {
            itemAlbumBinding.txtName.text = album.artistName
            itemAlbumBinding.txtArtistName.text = album.collectionName
            itemAlbumBinding.txtPrice.text = "$" + album.collectionPrice.toString()
            itemAlbumBinding.txtReleaseDate.text = dateParse.format(dateParse.parse(album.releaseDate)!!)

            val bookmarkIconRes = if (album.isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_border
            itemAlbumBinding.btnBookMark.setImageResource(bookmarkIconRes)

            Glide.with(context).load(album.artworkUrl100).timeout(10000).into(itemAlbumBinding.imgAlbum)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        holder.bind(album)
    }

    interface BookMarkOnclickListener {
        fun onBookmarkClicked(album: Album)
    }
}