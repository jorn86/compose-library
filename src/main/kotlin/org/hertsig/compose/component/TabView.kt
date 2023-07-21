package org.hertsig.compose.component

import androidx.compose.foundation.layout.height
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TabBuilder(
    val title: @Composable () -> Unit,
    val content: @Composable () -> Unit,
) {
    constructor(name: String, content: @Composable () -> Unit): this({ TextLine(name) }, content)
}

@Composable
fun TabView(
    vararg views: TabBuilder,
    rowHeight: Dp = 32.dp,
    indexState: MutableState<Int> = remember { mutableStateOf(0) },
) = TabView(views.asList(), rowHeight, indexState)

@Composable
fun TabView(
    views: List<TabBuilder>,
    rowHeight: Dp = 40.dp,
    indexState: MutableState<Int> = remember { mutableStateOf(0) },
) {
    var currentIndex by indexState
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(color = LocalContentColor.current)
    ) {
        TabRow(currentIndex, Modifier.height(rowHeight)) {
            views.forEachIndexed { index, it ->
                Tab(currentIndex == index, { currentIndex = index }) {
                    it.title()
                }
            }
        }
    }
    views.getOrNull(currentIndex)?.let { it.content() }
}
