package org.hertsig.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CursorDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <V> DropdownMenu(
    showState: MutableState<Boolean>,
    values: Collection<V>,
    display: (V) -> String = { it.toString() },
    itemAlign: TextAlign = TextAlign.Start,
    onUpdate: (V) -> Unit
) {
    var show by showState
    DropdownMenu(show, values, { show = false }, display, itemAlign, onUpdate)
}

@Composable
fun <V> DropdownMenu(
    visible: Boolean,
    values: Collection<V>,
    onDismiss: () -> Unit = {},
    display: (V) -> String = { it.toString() },
    itemAlign: TextAlign = TextAlign.Start,
    onUpdate: (V) -> Unit
) {
    CursorDropdownMenu(visible, onDismiss) {
        values.forEach { Item(display(it), itemAlign) { onUpdate(it) } }
    }
}

@Composable
internal fun Item(text: String, align: TextAlign = TextAlign.Start, onClick: () -> Unit) {
    TextLine(text, Modifier.padding(2.dp).defaultMinSize(40.dp).fillMaxWidth().clickable { onClick() }, align = align)
}
