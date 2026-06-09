package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TheologySection(viewModel: ReberViewModel) {
    var theologyTab by remember { mutableStateOf(0) } // 0 = Islam & Iman, 1 = Ibn Taymiyyah

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("theology_section_container"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tab Headers
        TabRow(
            selectedTabIndex = theologyTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = theologyTab == 0,
                onClick = { theologyTab = 0 },
                modifier = Modifier.testTag("theology_tab_islam_iman"),
                text = {
                    Text(
                        "ئیسلام و ئیمان",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            )
            Tab(
                selected = theologyTab == 1,
                onClick = { theologyTab = 1 },
                modifier = Modifier.testTag("theology_tab_ibn_taymiyyah"),
                text = {
                    Text(
                        "ئیبن تەیمیە و مێژوو",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (theologyTab == 0) {
                // Islam & Iman
                item {
                    Text(
                        text = "جیاوازی نێوان ئیسلام و ئیمان",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "کاتێک باسی زمانەوانی و شەرعی دەکەین لەسەر ئەم دوو چەمکە گرنگە.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                }

                // Interactive Circle Rule Diagram
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "یاسای کۆبوونەوە و جیابوونەوە",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "«إذا اجتمعا افترقا، وإذا افترقا اجتمعا»",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // Graphic layout
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Islam circle (Outer)
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "ئیسلام",
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "کردەوە دەرەکییەکان",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                    )
                                }

                                Text(
                                    text = "↔",
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Bold
                                )

                                // Iman circle (Inner)
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f))
                                            .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "ئیمان",
                                            color = MaterialTheme.colorScheme.tertiary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "باوەڕی ناو دڵ",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "واتای سادە بە کوردی: ئەگەر هەردوو پێکەوە لە یەک دەق و ڕستەدا باسكران، ئیسلام دەبێتە نیشانەی کردەوە دەرەکییەکان و ئیمانیش دەبێتە نیشانەی ناو دڵ. بەڵام ئەگەر هەر یەکەیان بە تەنیا باس کران، هەمان واتای هەموو ئایین بە یەکەوە دەگەیەنن.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 20.sp,
                                    textDirection = TextDirection.Rtl,
                                    textAlign = TextAlign.Justify
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }

                // Core Explanation Content
                item {
                    var isExpanded by remember { mutableStateOf(false) }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isExpanded = !isExpanded },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(1.dp),
                        shape = RoundedCornerShape(12.dp)
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
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Expand",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "بەڵگەی قورئانی جیاوازییەکە (سوورەتی الحجرات)",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "کاتێک عەرەبەکانی دەشتەکی سوور دەبنەوە لەسەر گەیشتن بە لوتکەی باوەڕ، خوای گەورە ڕاستیان دەکاتەوە:",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                    textDirection = TextDirection.Rtl
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )

                            AnimatedVisibility(visible = isExpanded) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "﴿قَالَتِ الْأَعْرَابُ آمَنَّا ۖ قُل لَّمْ تُؤْمِنُوا وَلَٰكِن قُولُوا أَسْلَمْنَا وَلَمَّا يَدْخُلِ الْإِيمَانُ فِي قُلُوبِكُمْ...﴾",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = MaterialTheme.colorScheme.tertiary,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.08f))
                                            .padding(12.dp)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "واتا: کۆچەرییەکان وتیان باوەڕمان هێنا (ئیمانمان لوتکەیە)، پێیان بڵێ: ئێوە هێشتا ئیمانتان نەچووەتە قوڵایی دڵتانەوە، بەڵکو بڵێن موسڵمان بووین (ئیسلاممان هێنا بە نوێژ و شایەتمان). ئەمە پشتڕاستی دەکاتەوە کە ئیمان پلەیەکی زۆر بەرزتر و دڵییە لەچاو ناسنامەی فەرمی ئیسلام.",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            lineHeight = 22.sp,
                                            textDirection = TextDirection.Rtl,
                                            textAlign = TextAlign.Justify
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Button(
                                        onClick = {
                                            viewModel.toggleBookmark(
                                                type = "theology",
                                                title = "ئیسلام و ئیمان (یاسای زمانەوانی)",
                                                subtitle = "ئایەتی سوورەتی الحجرات ١٤",
                                                details = "شیکردنەوەی تەواوی جیاوازی ئیسلام و ئیمان بەپێی یاسای کۆبوونەوە و جیابوونەوە (إذا اجتمعا افترقا...) و ئایەتی ١٤ی سوورەتی الحجرات."
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                            contentColor = MaterialTheme.colorScheme.primary
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("خەزنکردن لە نیشانەکراوەکان")
                                    }
                                }
                            }
                        }
                    }
                }

                // General summary points
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "نیشانە سەرەکییەکانی پێکداچوونەکە:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Right
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val items = listOf(
                                "ئیسلام بوارێکی گشتی و یاساییە بۆ خوێنپارێزی و ژیانی موسڵمانان.",
                                "ئیمان کارێکی دڵییە و پەیوەندی بە خوداوەندەوە هەیە و مەرجی سەرەکی دەربازبوونی قیامەتە.",
                                "هەموو مؤمن (باوەڕدار)ێک موسڵمانە، بەڵام هەموو موسڵمانێک مەرج نییە بڕوادار (مؤمن)بێت.",
                                "فەرموودەی جوبرەئیل کاتێک لێکیان جیا دەکاتەوە لوتکەی زیرەکی دەقەکە نیشان پێدەدا."
                            )
                            items.forEach { pt ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = pt,
                                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("•", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            } else {
                // Ibn Taymiyyah biography history
                item {
                    Text(
                        text = "مێژووی ژیان و باری کەسی ئیبن تەیمیە",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "وەڵامی ڕاستی لە سەرچاوە زانستییە باوەڕپێکراوەکان دەربارەی دۆخی هاوسەرگیری و کەنیزەکی شێخولئیسلام.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                }

                // Fact 1: Celibate
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "ئایە ئیبن تەیمیە ژنی هێناوە؟",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "نەخێر، بەپێی گێڕانەوەی هەموو مێژوونووسان (وەک ئەلذەهەبی و ئیبن کەسیر)، ئیبن تەیمیە تا کۆتایی تەمەنی بە سەڵتی ماوەتەوە و ژنی نەهێناوە. هۆکاری ئەمەش سەرقاڵی تەواوی بووە بە زانست، خوێندن، وانەوتنەوەی بەردەوام، نووسینی بەسەدان بەرگ پەڕتووک، هەروەها تێکۆشان و بەشداری جەنگ دژی تاتارەکان و زیندانی بەردەوام کە دەرفەتی جێگیری ژیانی بۆ دروست نەکردبوو.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 22.sp,
                                    textDirection = TextDirection.Rtl,
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }
                    }
                }

                // Fact 2: Abul Abbas
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "ناونازناوی باوکی (أبو العباس) بەبێ منداڵ",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "هەرچەندە نازناوی ئیبن تەیمیە بوو بە (باوکی عەباس - أبو العباس)، بەڵام ئەمە بەو مانایە نییە کە ئەو کوڕی هەبووبێت. لە فەرهەنگ و نەریتی دێرینی عەرەبیدا عادەتێکی باو بووە کە تەنانەت زانا و کەسە سەڵتەکانیش کونیە (نازناو)ی تایبەتی خۆیان هەبێت لە جەمسەری ڕێزگرتن و پێوەندی کۆمەڵایەتیدا.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 22.sp,
                                    textDirection = TextDirection.Rtl,
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }
                    }
                }

                // Fact 3: Concubines
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "ئایە کەنیزەک و ماڵ و کەرەستەی خۆشی هەبووە؟",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "نەخێر، ئیبن تەیمیە هیچ کەنیزەکێکی نەبووە. ژیانی ئەو لە لوتکەی سادەیی و دونیانەویستیدا (الزهد) بوو. لە هۆدەیەکی بچووکدا دەژیا و زۆربەی ژیانی لە سەفەر، فێرگە یان زیندانی دەسەڵاتداراندا بووە. هیچ موڵکێکی تایبەتی یان کۆشک و سامانی نەبووە بۆ ئەوەی کەنیزەک ڕابگرێ. بە تەواوی هاوپۆلی زانایانی گەورەی وەکو ئیمامی نەوەوی بووە لەم دۆخەدا.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 22.sp,
                                    textDirection = TextDirection.Rtl,
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }
                    }
                }

                // Ceilbate book
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.05f)),
                        shape = RoundedCornerShape(12.dp),
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "کتێبی 'العلماء العزاب' ـ زانا سەڵتەکان",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    textAlign = TextAlign.Right
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.Info, contentDescription = "Info", tint = MaterialTheme.colorScheme.tertiary)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "زانای گەورە شێخ عەبدولفەتاح ئەبو غوددە کتێبێکی ناوازەی نووسیوە بە ناونیشانی (العلماء العزاب الذين آثروا العلم على الزواج) واتە 'ئەو زانایە سەڵتانەی کە زانست بەپێش هاوسەرگیری خست'. لەوێدا ناوی ئیبن تەیمیە هاوشانی چەندین عەقڵی نموونەیی تری مێژوو وەک ئیمامی طبری، نەوەوی، دەهەبی و زانای تر جێگیر دەکات کە خزمەتی زمان و فەرموودەیان لەپێش ژیانی تایبەتی دانا.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 20.sp,
                                    textDirection = TextDirection.Rtl,
                                    textAlign = TextAlign.Justify
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    viewModel.toggleBookmark(
                                        type = "theology",
                                        title = "ئیبن تەیمیە و هاوسەرگیری",
                                        subtitle = "کورتەی مێژوونووسان",
                                        details = "زانیاری لەسەر ئەوەی بۆچی ئیبن تەیمیە سەڵت بووە و خاوەنی کەنیزەک نەبووە، پاڵپشت بە کتێبی العلماء العزاب بۆ شێخ عەبدولفەتاح ئەبو غوددە."
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = MaterialTheme.colorScheme.onTertiary
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("خەزنکردنی مێژووەکە")
                            }
                        }
                    }
                }
            }
        }
    }
}
