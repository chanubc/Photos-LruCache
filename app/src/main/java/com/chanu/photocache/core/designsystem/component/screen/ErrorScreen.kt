package com.chanu.photocache.core.designsystem.component.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chanu.photocache.core.designsystem.type.MessageType
import com.teamwable.designsystem.component.screen.EmptyTextScreen

@Composable
fun ErrorScreen(
    type: MessageType = MessageType.ERROR,
    onClick: () -> Unit = {},
) {
    EmptyTextScreen(
        type = type,
        content = {
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = onClick,
            ) {
                Text(text = "Retry")
            }
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun ErrorScreenPreView() {
    ErrorScreen()
}
