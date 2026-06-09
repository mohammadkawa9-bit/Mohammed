package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.first

data class RulingItem(
    val title: String,
    val subtitle: String,
    val text: String,
    val reference: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DietarySection(viewModel: ReberViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val isScanning by viewModel.isScanning.collectAsState()
    val ingredientResult by viewModel.ingredientResult.collectAsState()

    val rulings = remember {
        listOf(
            RulingItem(
                title = "گۆشتی بەراز (گۆشتی بەراز و بەشەکانی)",
                subtitle = "حەرامکراوە بە کۆدەنگی تەواو و دەقی قورئانی",
                text = "خواردنی گۆشت، چەوری، پێست و هەموو خانەیەکی بەراز بە یەکدەنگی زانایانی ئیسلام حەرامکراوە. لەناو قورئانی پیرۆزدا لە چوار شوێن لە سوورەتەکانی جیاواز بە ڕوونی ناوی هاتووە کە دیارترینیان لە دەستپێکی سوورەتی المائدة دایە. حەرامکردنەکە ڕیزی لە جەستەی کاتی و تەندروستی مرۆڤ دەگرێت، بەهۆی پیسی پێکهاتەی گۆشتەکە و ڕێژەی زۆری بڕوانە کرمی ژەهراوی.",
                reference = "سوورەتی المائدة، ئایەتی ٣: ﴿حُرِّمَتْ عَلَيْكُمُ الْمَيْتَةُ وَالدَّمُ وَلَحْمُ الْخِنزِيرِ...﴾"
            ),
            RulingItem(
                title = "گۆشتی سەگ",
                subtitle = "حەرامێتی سەگ بەپێی سوننەتی فەرموودە",
                text = "خواردنی گۆشتی سەگ بەپێی فەرموودەی پێغەمبەر (د.خ) حەرامە کە ڕێگری کرد لە خواردنی هەر گیانلەبەرێکی دڕندە کە خاوەنی کەڵبە و دانی تیژ بێت (کل ذی ناب من السباع). بەپێی مەزهەبی فەرمی زانایان سەگ پێویستە تەنها بۆ پاسەوانی یان ڕاوکردن بەکاربێت و خواردنی بە فیتڕەتی مرۆڤیش پڕە لە سەختی بێبایەخی. مەزهەبی مالیکی زانایان دەڵێن کە جەستەی سەگ خۆی پاکە بەڵام ناوبژی لە دەم و تفیدایە کاتێک دەخرێتە سەر دەفری چێشت.",
                reference = "فەرموودەی سەحیح (موسلیم): «كُلُّ ذِي نَابٍ مِنَ السِّبَاعِ فَأَكْلُهُ حَرَامٌ»"
            ),
            RulingItem(
                title = "گۆشتی مرۆڤ (شکۆی مرۆڤایەتی)",
                subtitle = "حەرامێتی لەپێناو بەرزڕاگرتن نەک پیسی",
                text = "جەستەی ئادەمیزاد شکۆمەندکراوە لەلایەن خوای گەورەوە (تەحریمی فەخری نەک نەجاسەتی). گۆشتی مرۆڤ وەک پاکێتی دەبێت پارێزراو بێت لە تەنانەت چواندنەکان؛ بۆ پیشاندانی قێزەونی غەیبەت لە قورئاندا ئاماژە بە خواردنی گۆشتی برای مردوو کراوە. بۆیە گۆشتی مرۆڤ بە هیچ جۆرێک لە کایەی خۆراکدا جێی نابێتەوە تەنانەت چێشتکەری ناچاری لە کاتی برسییەتی زۆریشدا، بۆ پاراستنی هەیبەتی مرۆڤ.",
                reference = "سوورەتی الإسراء: ﴿وَلَقَدْ كَرَّمْنَا بَنِي آدَمَ﴾ & سوورەتی الحجرات: ﴿أَيُحِبُّ أَحَدُكُمْ أَن يَأْكُلَ لَحْمَ أَخِيهِ مَيْتًا...﴾"
            )
        )
    }

    val filteredRulings = rulings.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.text.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("dietary_scroll_container"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Core header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ڕێبەری خاوێنی و گۆشتە حەرامکراوەکان",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "زانیاری لەسەر بنەما شەرعی و تەندروستییەکانی خواردن لە قورئان و سوننەتدا لەگەڵ بەڵگەکان.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        // Search Field
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dietary_search_input"),
                placeholder = { Text("گەڕان لەناو حوکمەکاندا...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "گەڕان") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Intelligent ingrediet scanner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder()
            ) {
                var ingredientInput by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "زیرەکی دەستکرد AI",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Text(
                            text = "پشکنەری براند و پێکهاتەی نادیار",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = ingredientInput,
                        onValueChange = { ingredientInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("ingredient_input"),
                        placeholder = {
                            Text(
                                "بۆ نموونە: شۆربای ماجی (Maggi soup)، تامی دەستکرد، رۆنی هایدرۆجین، جەلاتینی سەوزە یان بەراز، کارمین یان رەنگی پیتی E120...",
                                fontSize = 13.sp,
                                textAlign = TextAlign.Right
                            )
                        },
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (ingredientInput.isNotBlank()) {
                                viewModel.checkIngredients(ingredientInput)
                            }
                        },
                        enabled = !isScanning && ingredientInput.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("scan_ingredients_button")
                    ) {
                        if (isScanning) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("لە پشکنیندایە...", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        } else {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Scan")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("پشکنین و شیکردنەوەی پێکهاتەکە", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }

                    AnimatedVisibility(visible = !ingredientResult.isNullOrBlank()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                                .padding(12.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        viewModel.toggleBookmark(
                                            type = "dietary",
                                            title = "پشکنینی: ${ingredientInput.take(20)}...",
                                            subtitle = "شیکردنەوەی شەرعی و تەندروستی AI",
                                            details = ingredientResult ?: ""
                                        )
                                    }
                                ) {
                                    val isSaved = remember { mutableStateOf(false) }
                                    LaunchedEffect(ingredientResult) {
                                        viewModel.isBookmarked("dietary", "پشکنینی: ${ingredientInput.take(20)}...").collect {
                                            isSaved.value = it
                                        }
                                    }
                                    Icon(
                                        imageVector = if (isSaved.value) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "Save",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Text(
                                    text = "ئەنجامی پشکنین",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = ingredientResult ?: "",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 22.sp,
                                    textDirection = TextDirection.Rtl,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }
        }

        // Header for rulings
        item {
            Text(
                text = "حوکمە نەگۆڕەکانی قورئان و سوننەت",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Right
            )
        }

        // List of rulings
        items(filteredRulings) { ruling ->
            var isSaved by remember { mutableStateOf(false) }
            LaunchedEffect(ruling.title) {
                viewModel.isBookmarked("dietary", ruling.title).collect {
                    isSaved = it
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ruling_card_${ruling.title.take(5)}"),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            modifier = Modifier.testTag("bookmark_button_${ruling.title.take(5)}"),
                            onClick = {
                                viewModel.toggleBookmark(
                                    type = "dietary",
                                    title = ruling.title,
                                    subtitle = ruling.subtitle,
                                    details = ruling.text + "\n\nسەرچاوە: " + ruling.reference
                                )
                            }
                        ) {
                            Icon(
                                imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = ruling.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                textAlign = TextAlign.Right
                            )
                            Text(
                                text = ruling.subtitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                textAlign = TextAlign.Right
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = ruling.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            textDirection = TextDirection.Rtl,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = ruling.reference,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary,
                            textDirection = TextDirection.Rtl
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f))
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (filteredRulings.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "هیچ حوکمێک نەدۆزرایەوە لەگەڵ مەرجی گەڕانەکەت.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
