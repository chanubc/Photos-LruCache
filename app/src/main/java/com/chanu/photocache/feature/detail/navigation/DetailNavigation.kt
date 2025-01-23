package com.chanu.photocache.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chanu.photocache.core.navigation.Route

fun NavController.navigateDetail() {
    this.navigate(Route.Detail)
}

fun NavGraphBuilder.detailNavGraph(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Detail> {
    }
}
