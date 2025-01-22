package com.chanu.photocache.feature.home.naviagation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chanu.photocache.core.navigation.Route

fun NavGraphBuilder.homeNavGraph(
    navigateToHome: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Home> {
    }
}
