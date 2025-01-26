package com.chanu.photocache.feature.detail.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.chanu.photocache.R

@Composable
fun RowScope.DetailButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = dimensionResource(id = R.dimen.btn_horizontal_padding)),
    ) {
        Text(text = text)
    }
}
