package com.chanu.photocache.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.navigation.Route
import com.chanu.photocache.feature.detail.navigation.navigateDetail

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Route.Home

    fun navigateRetail(photoModel: PhotoModel) {
        navController.navigateDetail(photoModel)
    }

    fun isBackStackNotEmpty(): Boolean = navController.previousBackStackEntry != null

    fun navigateUp() {
        navController.navigateUp()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
