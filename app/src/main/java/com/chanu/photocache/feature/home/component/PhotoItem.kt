package com.chanu.photocache.feature.home.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun PhotoItem(
    bitmap: Bitmap?,
    onLoad: () -> Unit,
) {
    LaunchedEffect(Unit) {
        if (bitmap == null) onLoad()
    }

    PhotoContent(bitmap)
}

@Composable
fun PhotoContent(bitmap: Bitmap?) {
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.aspectRatio(1f),
        )
    } else {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.LightGray),
        )
    }
}
