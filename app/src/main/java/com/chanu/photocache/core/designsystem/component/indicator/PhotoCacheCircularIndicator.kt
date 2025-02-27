package com.chanu.photocache.core.designsystem.component.indicator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PhotoCacheCircularIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    color: Color = Color.Gray,
    strokeWidth: Dp = 3.dp,
    trackColor: Color = Color.LightGray,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .size(size)
                .padding(5.dp),
            color = color,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
            trackColor = trackColor,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CircularIndicatorPreview() {
    PhotoCacheCircularIndicator()
}
