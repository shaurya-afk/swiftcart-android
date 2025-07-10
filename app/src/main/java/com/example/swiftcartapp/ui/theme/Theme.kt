package com.example.swiftcartapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Dark Mode Scheme
private val DarkColorScheme = darkColorScheme(
    background     = MonoPrimary,       // pure black canvas
    surface        = MonoPrimaryDark,   // slightly lifted cards & sheets
    primary        = MonoAccent,        // buttons, fab, toggles
    secondary      = MonoSurface,       // subtle highlights
    tertiary       = MonoOutline,       // outlines, dividers
    onBackground   = MonoOnPrimary,     // white text/icons on bg
    onSurface      = MonoOnPrimary,     // white text/icons on surfaces
    onPrimary      = MonoOnPrimary,     // white on accent
    onSecondary    = MonoPrimaryDark,   // dark text on light secondary
    onTertiary     = MonoPrimaryDark    // dark on outline
)

// Light Mode Scheme
private val LightColorScheme = lightColorScheme(
    background     = MonoBg,            // bright white canvas
    surface        = MonoSurface,       // light gray cards
    primary        = MonoPrimary,       // black buttons & text
    secondary      = MonoAccent,        // gray accents
    tertiary       = MonoOutline,       // silver outlines
    onBackground   = MonoOnSurface,     // deep gray text on white bg
    onSurface      = MonoOnSurface,     // deep gray text on gray surface
    onPrimary      = MonoOnPrimary,     // white on black
    onSecondary    = MonoOnPrimary,     // white on gray accent
    onTertiary     = MonoPrimaryDark    // dark text on silver
)

@Composable
fun SwiftCartAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}