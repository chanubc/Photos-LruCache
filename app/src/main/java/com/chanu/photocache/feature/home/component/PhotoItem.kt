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
import androidx.compose.ui.layout.ContentScale
import com.chanu.photocache.core.designsystem.extension.modifier.noRippleClickable

@Composable
fun PhotoItem(
    bitmap: Bitmap?,
    onLoad: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        if (bitmap == null) onLoad()
    }

    PhotoContent(
        bitmap = bitmap,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun PhotoContent(
    bitmap: Bitmap?,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .aspectRatio(1f)
                .noRippleClickable { onClick() },
        )
    } else {
        Box(
            modifier = modifier
                .aspectRatio(1f)
                .background(Color.LightGray),
        )
    }
}
