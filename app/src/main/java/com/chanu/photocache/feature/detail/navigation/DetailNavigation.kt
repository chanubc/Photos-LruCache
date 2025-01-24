package com.chanu.photocache.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chanu.photocache.core.navigation.Route
import com.chanu.photocache.feature.detail.DetailRoute

fun NavController.navigateDetail(id: String) {
    this.navigate(Route.Detail(id = id))
}

fun NavGraphBuilder.detailNavGraph(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Detail> {
        DetailRoute(
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}
