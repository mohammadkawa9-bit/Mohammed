package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = SoftGoldDark,
  secondary = SecondaryGreen,
  tertiary = AccentGold,
  background = DarkBackground,
  surface = DarkSurface,
  onPrimary = DarkBackground,
  onSecondary = TextLight,
  onBackground = TextLight,
  onSurface = TextLight,
  outline = DarkStroke
)

private val LightColorScheme = lightColorScheme(
  primary = IslamicGreen,
  secondary = SecondaryGreen,
  tertiary = WarmGold,
  background = CreamBackground,
  surface = CardBeige,
  onPrimary = OffWhite,
  onSecondary = TextCharcoal,
  onBackground = TextCharcoal,
  onSurface = TextCharcoal,
  outline = CardBeige
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Custom green-and-gold is our signature visual direction, disable dynamic overlay by default
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
