package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Bookmark

@Composable
fun BookmarksSection(viewModel: ReberViewModel) {
    val bookmarks by viewModel.bookmarks.collectAsState()
    var selectedBookmark by remember { mutableStateOf<Bookmark?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("bookmarks_section_container")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.04f))
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "نیشانەکراوەکان و مێژوو",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (bookmarks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FolderOpen,
                        contentDescription = "No bookmarks",
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "هیچ نیشانەکراوێک بەردەست نییە",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "دەتوانیت هەر حوکمێک، وەڵامێکی چاتی زیڕەک یان ڕاگەیەندراو خەزن بکەیت لێرەدا دەردەکەون.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bookmarks) { bookmark ->
                    BookmarkListItem(
                        bookmark = bookmark,
                        onDelete = { viewModel.deleteBookmark(bookmark) },
                        onClick = { selectedBookmark = bookmark }
                    )
                }
            }
        }
    }

    // Detail modal dialog
    if (selectedBookmark != null) {
        AlertDialog(
            onDismissRequest = { selectedBookmark = null },
            confirmButton = {
                Button(
                    onClick = { selectedBookmark = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("داخستن")
                }
            },
            icon = {
                val icon = when (selectedBookmark?.type) {
                    "dietary" -> Icons.Default.MenuBook
                    "chat" -> Icons.Default.Chat
                    "announcement" -> Icons.Default.Campaign
                    else -> Icons.Default.Book
                }
                Icon(icon, contentDescription = selectedBookmark?.type, tint = MaterialTheme.colorScheme.primary)
            },
            title = {
                Text(
                    text = selectedBookmark?.title ?: "",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 360.dp)
                        .verticalScroll(androidx.compose.foundation.rememberScrollState()),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = selectedBookmark?.subtitle ?: "",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = selectedBookmark?.details ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            textDirection = TextDirection.Rtl
                        ),
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}


@Composable
fun BookmarkListItem(
    bookmark: Bookmark,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val categoryIcon = when (bookmark.type) {
        "dietary" -> Icons.Default.MenuBook
        "chat" -> Icons.Default.Chat
        "announcement" -> Icons.Default.Campaign
        else -> Icons.Default.Book
    }

    val typeLabel = when (bookmark.type) {
        "dietary" -> "پاک وخاوێنی"
        "chat" -> "چاتی ڕێبەر"
        "announcement" -> "ڕاگەیەندراو"
        "theology" -> "بیروباوەڕ"
        else -> "گشتی"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("bookmark_item_${bookmark.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_bookmark_${bookmark.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkRemove,
                    contentDescription = "سڕینەوە",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = typeLabel,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = bookmark.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Right
                        )
                    }
                    Text(
                        text = bookmark.subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.secondary),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Right
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = categoryIcon,
                        contentDescription = typeLabel,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
