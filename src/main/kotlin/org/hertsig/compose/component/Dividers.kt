package org.hertsig.compose.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun defaultColor() = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)

@Composable
fun HorizontalDivider(color: Color = defaultColor(), thickness: Dp = 1.dp) {
    Divider(Modifier.fillMaxWidth().height(thickness))
}

@Composable
fun VerticalDivider(color: Color = defaultColor(), thickness: Dp = 1.dp) {
    Divider(Modifier.fillMaxHeight().width(thickness))
}
