package com.chanu.photocache.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chanu.photocache.core.model.PhotoModel
import com.chanu.photocache.core.navigation.Route
import com.chanu.photocache.core.navigation.parcelableNavType
import com.chanu.photocache.feature.detail.DetailRoute
import kotlin.reflect.typeOf

fun NavController.navigateDetail(photoModel: PhotoModel) {
    this.navigate(Route.Detail(photoModel = photoModel))
}

fun NavGraphBuilder.detailNavGraph(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Detail>(
        typeMap = DetailTypeMap.typeMap,
    ) {
        DetailRoute(
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}

object DetailTypeMap {
    val typeMap = mapOf(
        typeOf<PhotoModel>() to parcelableNavType<PhotoModel>(),
    )
}
