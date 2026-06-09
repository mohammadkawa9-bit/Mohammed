package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.ReberViewModel
import com.example.ui.ChatSection
import com.example.ui.DietarySection
import com.example.ui.TheologySection
import com.example.ui.RamadanSection
import com.example.ui.BookmarksSection

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        MainScreen()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
  val viewModel: ReberViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
  var currentTab by remember { mutableStateOf(0) }

  Scaffold(
    modifier = Modifier.fillMaxSize().testTag("main_scaffold"),
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "ڕێبەر • Rêber",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.onPrimary
          )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.testTag("main_top_app_bar")
      )
    },
    bottomBar = {
      NavigationBar(
        modifier = Modifier.testTag("main_bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
      ) {
        NavigationBarItem(
          selected = currentTab == 0,
          onClick = { currentTab = 0 },
          icon = { Icon(Icons.Default.Chat, contentDescription = "چات") },
          label = { Text("چاتی زیرەک", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primary
          ),
          modifier = Modifier.testTag("nav_item_chat")
        )
        NavigationBarItem(
          selected = currentTab == 1,
          onClick = { currentTab = 1 },
          icon = { Icon(Icons.Default.MenuBook, contentDescription = "ڕێبەر") },
          label = { Text("خۆراک شیکار", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primary
          ),
          modifier = Modifier.testTag("nav_item_dietary")
        )
        NavigationBarItem(
          selected = currentTab == 2,
          onClick = { currentTab = 2 },
          icon = { Icon(Icons.Default.Book, contentDescription = "فیقهـ") },
          label = { Text("مێژوو و بیروباوەڕ", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primary
          ),
          modifier = Modifier.testTag("nav_item_theology")
        )
        NavigationBarItem(
          selected = currentTab == 3,
          onClick = { currentTab = 3 },
          icon = { Icon(Icons.Default.Campaign, contentDescription = "کاتەکان") },
          label = { Text("ڕەمەزاننامە", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primary
          ),
          modifier = Modifier.testTag("nav_item_ramadan")
        )
        NavigationBarItem(
          selected = currentTab == 4,
          onClick = { currentTab = 4 },
          icon = { Icon(Icons.Default.Bookmark, contentDescription = "نیشانە") },
          label = { Text("نیشانەکان", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primary
          ),
          modifier = Modifier.testTag("nav_item_bookmarks")
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      when (currentTab) {
        0 -> ChatSection(viewModel)
        1 -> DietarySection(viewModel)
        2 -> TheologySection(viewModel)
        3 -> RamadanSection(viewModel)
        4 -> BookmarksSection(viewModel)
      }
    }
  }
}
