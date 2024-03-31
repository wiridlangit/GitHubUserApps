package com.dicoding.restaurantreview.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.restaurantreview.database.FavoriteUser

class FavoriteDiffCallback(private val oldNoteList: List<FavoriteUser>, private val newNoteList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].username == newNoteList[newItemPosition].username

    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.avatarUrl == newNote.avatarUrl
    }
}