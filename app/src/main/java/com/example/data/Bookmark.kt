package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "dietary", "theology", "announcement", "chat"
    val title: String,
    val subtitle: String,
    val details: String,
    val timestamp: Long = System.currentTimeMillis()
)
