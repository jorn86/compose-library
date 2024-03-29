package org.hertsig.compose.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Autocompleter(
    complete: (String) -> List<String>,
    modifier: Modifier = Modifier,
    hint: String? = null,
    textAlign: TextAlign = TextAlign.Start,
    itemAlign: TextAlign = TextAlign.Start,
    onSelect: (String) -> Unit = {},
) {
    var value by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(emptyList<String>()) }
    Row {
        BasicTextField(
            value, { value = it; results = complete(it) },
            modifier.border(1.dp, Color.Black).padding(2.dp),
            textStyle = TextStyle(textAlign = textAlign),
            singleLine = true, maxLines = 1
        ) { innerTextField ->
            if (hint != null && value.isEmpty()) {
                TextLine(hint, Modifier.alpha(.5f), style = TextStyle.Default)
            }
            innerTextField()
        }
        androidx.compose.material.DropdownMenu(results.isNotEmpty(), { results = emptyList() }, false) {
            results.forEach { Item((it), itemAlign) { onSelect(it); value = ""; results = emptyList() } }
        }
    }
}
