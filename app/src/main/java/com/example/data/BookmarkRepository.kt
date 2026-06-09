package com.example.data

import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {
    val allBookmarks: Flow<List<Bookmark>> = bookmarkDao.getAllBookmarks()

    suspend fun insert(bookmark: Bookmark) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun delete(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark)
    }

    suspend fun deleteByTypeAndTitle(type: String, title: String) {
        bookmarkDao.deleteBookmarkByTypeAndTitle(type, title)
    }

    fun isBookmarked(type: String, title: String): Flow<Boolean> {
        return bookmarkDao.isBookmarked(type, title)
    }
}
