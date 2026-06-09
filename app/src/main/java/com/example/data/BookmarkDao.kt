package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE type = :type AND title = :title")
    suspend fun deleteBookmarkByTypeAndTitle(type: String, title: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE type = :type AND title = :title)")
    fun isBookmarked(type: String, title: String): Flow<Boolean>
}
