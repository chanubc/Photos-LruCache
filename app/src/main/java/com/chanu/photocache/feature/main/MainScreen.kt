package com.chanu.photocache.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.chanu.photocache.core.common.util.getThrowableMessage
import com.chanu.photocache.feature.detail.navigation.detailNavGraph
import com.chanu.photocache.feature.home.naviagation.homeNavGraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SNACK_BAR_DURATION = 2000L

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val localContextResource = LocalContext.current.resources

    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            val job = launch {
                snackBarHostState.showSnackbar(message = getThrowableMessage(throwable, localContextResource))
            }
            delay(SNACK_BAR_DURATION)
            job.cancel()
        }
    }

    Scaffold(
        topBar = {},
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                ) {
                    homeNavGraph(
                        navigateToDetail = navigator::navigateRetail,
                        onShowErrorSnackBar = onShowErrorSnackBar,
                    )
                    detailNavGraph(
                        onShowErrorSnackBar = onShowErrorSnackBar,
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    )
}
