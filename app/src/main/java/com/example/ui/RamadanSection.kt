package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RamadanSection(viewModel: ReberViewModel) {
    val context = LocalContext.current
    val formattedText by viewModel.ramadanFormattedText.collectAsState()

    // Hours control
    var branchStartHour by remember { mutableStateOf("08:00") }
    var branchEndHour by remember { mutableStateOf("14:00") }
    var salesStartHour by remember { mutableStateOf("09:00") }
    var salesEndHour by remember { mutableStateOf("15:00") }

    // Sync state
    LaunchedEffect(branchStartHour, branchEndHour, salesStartHour, salesEndHour) {
        viewModel.generateRamadanNotice(branchStartHour, branchEndHour, salesStartHour, salesEndHour)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("ramadan_tab_container"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "ئامادەکاری ئۆفیس و کارەکانی ڕەمەزان",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "کاتەکانی دەوام لە لقی فەرمی و تیمی فرۆشتن بەپێی گۆڕانکارییەکانی مانگی ڕەمەزان بە زمانێکی فەرمی و ئاسان.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }

        // Timing Selectors Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "ڕێکخستنی کاتەکانی لقە گشتییەکان",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = branchEndHour,
                            onValueChange = { branchEndHour = it },
                            label = { Text("کۆتایی دەوام (لق)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("timing_branch_end"),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = branchStartHour,
                            onValueChange = { branchStartHour = it },
                            label = { Text("دەستپێک (لق)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("timing_branch_start"),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "ڕێکخستنی کات فرۆشتن (هەولێر، دهۆک، موسڵ)",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = salesEndHour,
                            onValueChange = { salesEndHour = it },
                            label = { Text("کۆتایی (فرۆشتن)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("timing_sales_end"),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = salesStartHour,
                            onValueChange = { salesStartHour = it },
                            label = { Text("دەستپێک (فرۆشتن)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("timing_sales_start"),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        // Preview Box Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                ),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "پێشبینینی ئاگادارینامە فەرمییەکە",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = formattedText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            textDirection = TextDirection.Rtl
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Reset button
                        Button(
                            onClick = {
                                viewModel.toggleBookmark(
                                    type = "announcement",
                                    title = "ڕاگەیەندراوی ڕەمەزان ($branchStartHour بۆ $branchEndHour)",
                                    subtitle = "ئاگاداری کاتەکانی دەوام",
                                    details = formattedText
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("save_announcement_button")
                        ) {
                            val isSaved = remember { mutableStateOf(false) }
                            LaunchedEffect(formattedText) {
                                viewModel.isBookmarked("announcement", "ڕاگەیەندراوی ڕەمەزان ($branchStartHour بۆ $branchEndHour)").collect {
                                    isSaved.value = it
                                }
                            }
                            Icon(
                                imageVector = if (isSaved.value) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "خەزنکردن"
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("کردنە نیشانە", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }

                        // Copy button
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Ramadan Working Hours", formattedText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "ئاگادارییەکە ڕوونووس کرا!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("copy_announcement_button")
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "روونووس")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("لەبەرگرتنەوە", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}
