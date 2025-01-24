package com.chanu.photocache.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoCacheTheme {
                val navigator: MainNavigator = rememberMainNavigator()
                MainScreen(navigator = navigator)
            }
        }
    }
}
