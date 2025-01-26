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
import androidx.compose.ui.tooling.preview.Preview
import com.chanu.photocache.core.designsystem.extension.modifier.applyBlurStyle
import com.chanu.photocache.core.designsystem.extension.modifier.noRippleClickable
import com.chanu.photocache.core.designsystem.theme.PhotoCacheTheme
import com.chanu.photocache.core.designsystem.type.ColorFilterType

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
    colorFilterType: ColorFilterType = ColorFilterType.DEFAULT,
    modifier: Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.LightGray.copy(alpha = 0.5f)),
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = colorFilterType.toColorFilter(),
                modifier = modifier
                    .aspectRatio(1f)
                    .applyBlurStyle(colorFilterType)
                    .noRippleClickable { onClick() },
            )
        }
        content()
    }
}



@Composable
@Preview(showBackground = true)
private fun PhotoItemPreview() {
    PhotoCacheTheme {
        PhotoContent(
            bitmap = null,
            onClick = {},
            modifier = Modifier,
        )
    }
}
