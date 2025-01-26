package com.teamwable.designsystem.component.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.chanu.photocache.core.designsystem.type.MessageType

@Composable
fun EmptyTextScreen(
    type: MessageType = MessageType.EMPTY,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(type.title),
            color = type.color,
            textAlign = TextAlign.Center,
        )
        content()
    }
}
