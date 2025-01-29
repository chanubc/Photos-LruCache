package com.chanu.photocache.feature.home.naviagation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.navigation.Route
import com.chanu.photocache.feature.home.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navigateToDetail: (PhotoModel) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Home> {
        HomeRoute(
            navigateToDetail = navigateToDetail,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}
